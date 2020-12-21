package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Message to mark the start of the race.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RaceStartMessage extends WiclaxMessage {

    private Instant dateTime;

    @Override
    public String toData() {
        return "RACESTART " + WiclaxDateFormatters.TIME_WITH_MILLIS_FORMATTER.format(dateTime);
    }
}
