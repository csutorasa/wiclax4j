package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.ClockResponse;
import com.github.csutorasa.wiclax.message.WiclaxMessage;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Request to get the current time from the clock.
 */
@RequiredArgsConstructor
public class GetClockRequestHandler extends WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getGetClockCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command) && data.isEmpty();
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        Instant dateTime = clientConnection.getClock().getDateTime();
        WiclaxMessage message = new ClockResponse(dateTime);
        send(clientConnection, message);
    }
}
