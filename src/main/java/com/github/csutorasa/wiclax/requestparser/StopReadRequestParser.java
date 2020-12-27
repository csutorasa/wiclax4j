package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.request.StopReadRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.RequiredArgsConstructor;

/**
 * Parser for {@link StopReadRequest}.
 */
@RequiredArgsConstructor
public class StopReadRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "STOPREAD";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getStopReadCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public WiclaxRequest parse(String data) {
        return new StopReadRequest();
    }
}
