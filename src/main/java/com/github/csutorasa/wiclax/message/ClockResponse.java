package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.WiclaxDateFormatters;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Response with current clock time.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClockResponse extends WiclaxMessage {

    private Instant dateTime;

    @Override
    public String toData() {
        return "CLOCK " + WiclaxDateFormatters.DATE_TIME_FORMATTER.format(dateTime);
    }
}
