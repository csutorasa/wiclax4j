package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.request.RewindRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Parser for {@link RewindRequest}.
 */
@RequiredArgsConstructor
public class RewindRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "REWIND";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getRewindCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public WiclaxRequest parse(String data) {
        String[] parts = data.split(" ");
        Instant from = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[0] + " " + parts[1]));
        Instant to = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[2] + " " + parts[3]));
        return new RewindRequest(from, to);
    }
}
