package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.response.ClockResponse;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Request to get the current time from the clock.
 */
@RequiredArgsConstructor
public class GetClockRequestHandler implements WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;
    private final WiclaxClock clock;
    private final ResponseSender responseSender;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getGetClockCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command) && data.isEmpty();
    }

    @Override
    public void handle(String data) {
        Instant dateTime = clock.getDateTime();
        WiclaxResponse message = new ClockResponse(dateTime);
        responseSender.send(message);
    }
}
