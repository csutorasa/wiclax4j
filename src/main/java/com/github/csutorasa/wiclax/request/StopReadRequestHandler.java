package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.response.ReadOkResponse;
import lombok.RequiredArgsConstructor;

/**
 * Handles the stop read event and responds with success.
 */
@RequiredArgsConstructor
public class StopReadRequestHandler implements WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "STOPREAD";

    private final WiclaxProtocolOptions protocolOptions;
    private final StopReadHandler handler;
    private final ResponseSender responseSender;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getStopReadCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public void handle(String data) {
        handler.run();
        responseSender.send(new ReadOkResponse());
    }
}
