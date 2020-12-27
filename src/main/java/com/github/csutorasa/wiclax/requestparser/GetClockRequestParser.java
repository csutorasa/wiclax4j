package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.request.GetClockRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.RequiredArgsConstructor;

/**
 * Parser for {@link GetClockRequest}.
 */
@RequiredArgsConstructor
public class GetClockRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getGetClockCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command) && data.isEmpty();
    }

    @Override
    public WiclaxRequest parse(String data) {
        return new GetClockRequest();
    }
}
