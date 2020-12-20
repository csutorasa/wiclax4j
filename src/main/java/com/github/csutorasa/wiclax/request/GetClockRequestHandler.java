package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.message.ClockResponse;
import com.github.csutorasa.wiclax.message.WiclaxMessage;

import java.time.Instant;

/**
 * Request to get the current time from the clock.
 */
public class GetClockRequestHandler extends WiclaxRequestHandler {
    private static final String COMMAND = "CLOCK";

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command) && data.isEmpty();
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        Instant dateTime = clientConnection.getClock().getDateTime();
        WiclaxMessage message = new ClockResponse(dateTime);
        send(clientConnection, message);
    }
}
