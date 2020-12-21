package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Rewind request to resend all acquisitions between the from and to times.
 */
@RequiredArgsConstructor
public class RewindRequestHandler extends WiclaxRequestHandler {
    private static final String DEFAULT_COMMAND = "REWIND";

    private final WiclaxProtocolOptions protocolOptions;
    private final RewindHandler rewindTask;

    @Override
    public boolean supports(String command, String data) {
        String expectedCommand = protocolOptions.get(WiclaxProtocolOptions::getRewindCommand).orElse(DEFAULT_COMMAND);
        return expectedCommand.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        String[] parts = data.split(" ");
        Instant from = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[0] + " " + parts[1]));
        Instant to = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[2] + " " + parts[3]));
        rewindTask.accept(from, to);
    }
}
