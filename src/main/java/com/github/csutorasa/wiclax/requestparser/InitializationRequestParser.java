package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.request.InitializationRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Parser for {@link InitializationRequest}.
 */
public class InitializationRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "HELLO";

    private final String expectedRequest;

    /**
     * Creates a new parser.
     *
     * @param protocolOptions protocol options
     */
    public InitializationRequestParser(WiclaxProtocolOptions protocolOptions) {
        expectedRequest = protocolOptions.get(WiclaxProtocolOptions::getCommandsForInitialization).orElse(DEFAULT_COMMAND);
    }

    @Override
    public WiclaxRequest parse(String request) {
        if (expectedRequest.equals(request)) {
            return new InitializationRequest();
        }
        return null;
    }
}
