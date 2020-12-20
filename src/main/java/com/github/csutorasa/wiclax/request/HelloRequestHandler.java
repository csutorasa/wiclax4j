package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;

public class HelloRequestHandler extends WiclaxRequestHandler {
    private static final String COMMAND = "HELLO";

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        // Connection is up
    }
}
