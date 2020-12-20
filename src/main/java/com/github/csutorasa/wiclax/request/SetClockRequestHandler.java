package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.message.ClockOkResponse;

import java.time.Instant;

public class SetClockRequestHandler extends WiclaxRequestHandler {
    private static final String COMMAND = "CLOCK";

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command) && !data.isEmpty();
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        Instant dateTime = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(data));
        clientConnection.getClock().setDateTime(dateTime);
        send(clientConnection, new ClockOkResponse());
    }
}
