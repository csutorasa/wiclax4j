package com.github.csutorasa.wiclax.formatter;

import com.github.csutorasa.wiclax.model.Acquisition;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Collection of common Date formatters.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WiclaxAcuqisitionFormatter {

    /**
     * Default acquisition formatter.
     */
    public static final WiclaxAcuqisitionFormatter DEFAULT_FORMATTER;

    static {
        DEFAULT_FORMATTER = ofSeparator(";");
    }

    private final Collection<Function<Acquisition, String>> parts;

    private WiclaxAcuqisitionFormatter(Collection<Function<Acquisition, String>> parts, String separator) {
        List<Function<Acquisition, String>> connectedParts = new ArrayList<>();
        Iterator<Function<Acquisition, String>> it = parts.iterator();
        while (it.hasNext()) {
            connectedParts.add(it.next());
            if (it.hasNext()) {
                connectedParts.add(acquisition -> separator);
            }
        }
        this.parts = connectedParts;
    }

    /**
     * Creates an acquisition formatter with fixed sized fields.
     *
     * @param pattern pattern
     * @return formatter
     */
    public static WiclaxAcuqisitionFormatter ofPattern(String pattern) {
        List<Function<Acquisition, String>> parts = new ArrayList<>();
        if (pattern.isEmpty()) {
            return new WiclaxAcuqisitionFormatter(parts);
        }
        char lastRead = pattern.charAt(0);
        int ack = 0;
        for (int i = 1; i < pattern.length(); i++) {
            char current = pattern.charAt(i);
            if (current == lastRead) {
                continue;
            }
            if (isDateCharacter(lastRead) && isDateCharacter(current)) {
                continue;
            }
            if (isKnownCharacter(lastRead)) {
                parts.add(formatWithSeparator(Character.toString(lastRead), i - ack));
            } else if (isDateCharacter(lastRead)) {
                parts.add(formatWithSeparator(pattern.substring(ack, i)));
            } else {
                char staticValue = lastRead;
                parts.add(acquisition -> Character.toString(staticValue));
            }
            lastRead = current;
            ack = i;
        }
        if (isKnownCharacter(lastRead)) {
            parts.add(formatWithSeparator(Character.toString(lastRead), pattern.length() - ack));
        } else if (isDateCharacter(lastRead)) {
            parts.add(formatWithSeparator(pattern.substring(ack)));
        } else {
            char staticValue = lastRead;
            parts.add(acquisition -> Character.toString(staticValue));
        }
        return new WiclaxAcuqisitionFormatter(parts);
    }

    /**
     * Creates an acquisition formatter with pattern and separator.
     *
     * @param pattern   pattern
     * @param separator separator
     * @return formatter
     */
    public static WiclaxAcuqisitionFormatter ofPatternAndSeparator(String pattern, String separator) {
        if (separator.isEmpty()) {
            return ofPattern(pattern);
        } else {
            List<Function<Acquisition, String>> parts = Arrays.stream(pattern.split(separator))
                    .map(WiclaxAcuqisitionFormatter::formatWithSeparator)
                    .collect(Collectors.toList());
            return new WiclaxAcuqisitionFormatter(parts, separator);
        }
    }

    /**
     * Creates a acquisition formatter the default fields and a custom separator.
     *
     * @param separator separator
     * @return formatter
     */
    public static WiclaxAcuqisitionFormatter ofSeparator(String separator) {
        List<Function<Acquisition, String>> parts = new ArrayList<>();
        parts.add(Acquisition::getChipId);
        parts.add(acquisition -> WiclaxDateFormatters.DATE_TIME_WITH_MILLIS_FORMATTER.format(acquisition.getDetectionTime()));
        parts.add(acquisition -> orEmpty(acquisition.getDeviceId()));
        parts.add(acquisition -> orEmpty(acquisition.getLap()));
        parts.add(acquisition -> orEmpty(acquisition.getBatteryLevel()));
        parts.add(acquisition -> acquisition.isRewind() ? "1" : "0");
        return new WiclaxAcuqisitionFormatter(parts, separator);
    }

    /**
     * Formats the acquisition.
     *
     * @param acquisition acquisition to format
     * @return formatted string
     */
    public String format(Acquisition acquisition) {
        return parts.stream()
                .map(p -> p.apply(acquisition))
                .collect(Collectors.joining());
    }

    private static boolean isKnownCharacter(char ch) {
        return ch == 'C' || ch == 'L' || ch == '@';
    }

    private static boolean isDateCharacter(char ch) {
        return ch == 'Y' || ch == 'M' || ch == 'D' || ch == 'h' || ch == 'm' || ch == 's' || ch == 'c';
    }

    private static Function<Acquisition, String> formatWithSeparator(String mask) {
        switch (mask) {
            case "C":
                return Acquisition::getChipId;
            case "L":
                return acquisition -> orEmpty(acquisition.getLap());
            case "@":
                return acquisition -> orEmpty(acquisition.getDeviceId());
            default:
                return acquisition -> WiclaxDateFormatters.createFormWiclaxPattern(mask)
                        .format(acquisition.getDetectionTime());
        }
    }

    private static Function<Acquisition, String> formatWithSeparator(String mask, int length) {
        switch (mask) {
            case "C":
                return acquisition -> toFixedLength(acquisition.getChipId(), length);
            case "L":
                return acquisition -> toFixedLength(acquisition.getLap(), length);
            case "@":
                return acquisition -> toFixedLength(acquisition.getDeviceId(), length);
            default:
                return acquisition -> WiclaxDateFormatters.createFormWiclaxPattern(mask)
                        .format(acquisition.getDetectionTime());
        }
    }

    private static String toFixedLength(Object obj, int length) {
        String str = orEmpty(obj);
        if (str.length() > length) {
            return str.substring(0, length);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = str.length(); i < length; i++) {
            sb.append(" ");
        }
        sb.append(str);
        return sb.toString();
    }

    private static String orEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
