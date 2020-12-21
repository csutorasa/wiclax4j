package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import lombok.RequiredArgsConstructor;

/**
 * First message of the connection. Requires no actions.
 */
@RequiredArgsConstructor
public class InitializationRequestHandler extends WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "HELLO";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getCommandsForInitialization).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        // Connection is up
    }
}
