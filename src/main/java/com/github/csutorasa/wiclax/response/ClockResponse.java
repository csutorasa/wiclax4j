package com.github.csutorasa.wiclax.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Response with current clock time.
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class ClockResponse implements WiclaxResponse {

    private final Instant dateTime;
    private final DateTimeFormatter formatter;

    /**
     * Creates a new response.
     *
     * @param dateTime time of the clock
     * @param format   datetime format
     */
    public ClockResponse(Instant dateTime, String format) {
        this.dateTime = dateTime;
        if (format == null) {
            this.formatter = DateTimeFormatter.ofPattern("'CLOCK' dd-MM-yyyy HH:mm:ss")
                    .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
        } else {
            this.formatter = DateTimeFormatter.ofPattern(format)
                    .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
        }
    }

    @Override
    public String toData() {
        return formatter.format(dateTime);
    }
}
