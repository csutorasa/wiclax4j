package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;

/**
 * Last message before closing the connection.
 */
public class ByeRequestHandler implements WiclaxRequestHandler {
    private static final String COMMAND = "BYE";

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command);
    }

    @Override
    public void handle(String data) {
        // Connection is closing
    }
}
