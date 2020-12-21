package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.ReadOkResponse;
import lombok.RequiredArgsConstructor;

/**
 * Handles the stop read event and responds with success.
 */
@RequiredArgsConstructor
public class StopReadRequestHandler extends WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "STOPREAD";

    private final WiclaxProtocolOptions protocolOptions;
    private final StopReadHandler handler;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getStopReadCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        handler.run();
        send(clientConnection, new ReadOkResponse());
    }
}
