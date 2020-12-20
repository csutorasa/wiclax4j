package com.github.csutorasa.wiclax;

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
}
