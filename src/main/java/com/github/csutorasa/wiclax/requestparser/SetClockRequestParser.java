package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.request.SetClockRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Parser for {@link SetClockRequest}.
 */
@RequiredArgsConstructor
public class SetClockRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand).map(c -> c.split(" ")[0])
                .orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command) && !data.isEmpty();
    }

    @Override
    public WiclaxRequest parse(String data) {
        DateTimeFormatter formatter = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand)
                .map(c -> c.substring(c.indexOf(" ") + 1))
                .map(WiclaxDateFormatters::createFormWiclaxPattern)
                .orElse(WiclaxDateFormatters.DATE_TIME_FORMATTER);
        Instant dateTime = Instant.from(formatter.parse(data));
        return new SetClockRequest(dateTime);
    }
}
