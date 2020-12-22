package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.response.ClockOkResponse;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Request to set the clock time.
 */
@RequiredArgsConstructor
public class SetClockRequestHandler implements WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;
    private final WiclaxClock clock;
    private final ResponseSender responseSender;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand).map(c -> c.split(" ")[0])
                .orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command) && !data.isEmpty();
    }

    @Override
    public void handle(String data) {
        DateTimeFormatter formatter = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand)
                .map(c -> c.substring(c.indexOf(" ") + 1))
                .map(WiclaxDateFormatters::createFormWiclaxPattern)
                .orElse(WiclaxDateFormatters.DATE_TIME_FORMATTER);
        Instant dateTime = Instant.from(formatter.parse(data));
        clock.setDateTime(dateTime);
        responseSender.send(new ClockOkResponse());
    }
}
