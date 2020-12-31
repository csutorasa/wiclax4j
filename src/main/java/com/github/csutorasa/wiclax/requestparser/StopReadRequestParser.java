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

    private final String expectedRequest;

    /**
     * Creates a new parser.
     *
     * @param protocolOptions protocol options
     */
    public StopReadRequestParser(WiclaxProtocolOptions protocolOptions) {
        expectedRequest = protocolOptions.get(WiclaxProtocolOptions::getStopReadCommand).orElse(DEFAULT_COMMAND);
    }

    @Override
    public WiclaxRequest parse(String request) {
        if (expectedRequest.equals(request)) {
            return new StopReadRequest();
        }
        return null;
    }
}
