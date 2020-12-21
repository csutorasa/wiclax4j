package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.ReadOkResponse;
import lombok.RequiredArgsConstructor;

/**
 * Handles the start read event and responds with success.
 */
@RequiredArgsConstructor
public class StartReadRequestHandler extends WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "STARTREAD";

    private final WiclaxProtocolOptions protocolOptions;
    private final StartReadHandler handler;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getStartReadCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        handler.run();
        send(clientConnection, new ReadOkResponse());
    }
}
