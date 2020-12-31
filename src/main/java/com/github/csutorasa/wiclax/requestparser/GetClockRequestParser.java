package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.request.GetClockRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Parser for {@link GetClockRequest}.
 */
public class GetClockRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final String expectedRequest;

    /**
     * Creates a new parser.
     *
     * @param protocolOptions protocol options
     */
    public GetClockRequestParser(WiclaxProtocolOptions protocolOptions) {
        expectedRequest = protocolOptions.get(WiclaxProtocolOptions::getGetClockCommand)
                .map(String::trim).orElse(DEFAULT_COMMAND);
    }

    @Override
    public WiclaxRequest parse(String request) {
        if (expectedRequest.equals(request)) {
            return new GetClockRequest();
        }
        return null;
    }
}
