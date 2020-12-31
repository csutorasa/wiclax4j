package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.request.StartReadRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Parser for {@link StartReadRequest}.
 */
public class StartReadRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "STARTREAD";

    private final String expectedRequest;

    /**
     * Creates a new parser.
     *
     * @param protocolOptions protocol options
     */
    public StartReadRequestParser(WiclaxProtocolOptions protocolOptions) {
        expectedRequest = protocolOptions.get(WiclaxProtocolOptions::getStartReadCommand).orElse(DEFAULT_COMMAND);
    }

    @Override
    public WiclaxRequest parse(String request) {
        if (expectedRequest.equals(request)) {
            return new StartReadRequest();
        }
        return null;
    }
}
