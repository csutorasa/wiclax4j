package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.response.ReadOkResponse;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * Handles the start read event and responds with success.
 */
@RequiredArgsConstructor
public class StartReadRequestHandler implements WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "STARTREAD";

    private final WiclaxProtocolOptions protocolOptions;
    private final StartReadHandler handler;
    private final ResponseSender responseSender;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getStartReadCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public void handle(String data) {
        handler.run();
        responseSender.send(new ReadOkResponse());
    }
}
