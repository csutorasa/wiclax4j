package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Message to mark the start of the race.
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RaceStartMessage extends WiclaxMessage {

    private final Instant dateTime;

    @Override
    public String toData(WiclaxProtocolOptions protocolOptions) {
        return "RACESTART " + WiclaxDateFormatters.TIME_WITH_MILLIS_FORMATTER.format(dateTime);
    }
}
