package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.message.ClockOkResponse;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Request to set the clock time.
 */
@RequiredArgsConstructor
public class SetClockRequestHandler extends WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand).map(c -> c.split(" ")[0])
                .orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command) && !data.isEmpty();
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        DateTimeFormatter formatter = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand)
                .map(c -> c.substring(c.indexOf(" ") + 1))
                .map(WiclaxDateFormatters::createFormWiclaxPattern)
                .orElse(WiclaxDateFormatters.DATE_TIME_FORMATTER);
        Instant dateTime = Instant.from(formatter.parse(data));
        clientConnection.getClock().setDateTime(dateTime);
        send(clientConnection, new ClockOkResponse());
    }
}
