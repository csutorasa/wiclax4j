package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.message.ReadOkResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartReadRequestHandler extends WiclaxRequestHandler {
    private static final String COMMAND = "STARTREAD";

    private final StartReadHandler handler;

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        handler.run();
        send(clientConnection, new ReadOkResponse());
    }
}
