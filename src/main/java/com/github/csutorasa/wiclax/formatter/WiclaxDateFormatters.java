package com.github.csutorasa.wiclax.formatter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Collection of common Date formatters.
 */
public class WiclaxDateFormatters {
    /**
     * Date and time formatter. Example: 12-31-2020 23:59:59
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    /**
     * Date and time formatter. Example: 12-31-2020 23:59:59.999
     */
    public static final DateTimeFormatter DATE_TIME_WITH_MILLIS_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")
            .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    /**
     * Time formatter. Example: 23:59:59.999
     */
    public static final DateTimeFormatter TIME_WITH_MILLIS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
            .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());

    /**
     * Creates a new formatter from the Wiclax pattern.
     * {@code YYYYMMDDhhmmssccc} format is changed to {@code yyyyMMddHHmmssSSS}.
     *
     * @param pattern wiclax pattern
     * @return formatter
     */
    public static DateTimeFormatter createFormWiclaxPattern(String pattern) {
        StringBuilder p = new StringBuilder();
        boolean insideApostrophe = false;
        for (char c : pattern.toCharArray()) {
            if (c == '\'') {
                insideApostrophe = !insideApostrophe;
                p.append(c);
            } else if (insideApostrophe) {
                p.append(c);
            } else if (c == 'Y') {
                p.append("y");
            } else if (c == 'D') {
                p.append("d");
            } else if (c == 'h') {
                p.append("H");
            } else if (c == 'c') {
                p.append("S");
            } else {
                p.append(c);
            }
        }
        return DateTimeFormatter.ofPattern(p.toString())
                .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    /**
     * Creates a new formatter from the Wiclax pattern.
     * {@code yyyyMMddHHmmss} format is changed to {@code yyyyMMddHHmmss}.
     *
     * @param pattern pattern
     * @return formatter
     */
    public static DateTimeFormatter createFormWiclaxPattern2(String pattern) {
        return DateTimeFormatter.ofPattern(pattern)
                .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }
}
