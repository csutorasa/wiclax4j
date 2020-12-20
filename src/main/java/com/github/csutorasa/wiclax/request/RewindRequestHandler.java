package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxDateFormatters;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Rewind request to resend all acquisitions between the from and to times.
 */
@RequiredArgsConstructor
public class RewindRequestHandler extends WiclaxRequestHandler {
    private static final String COMMAND = "REWIND";

    private final RewindHandler rewindTask;

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command);
    }

    @Override
    public void handle(WiclaxClientConnection clientConnection, String data) {
        String[] parts = data.split(" ");
        Instant from = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[0] + " " + parts[1]));
        Instant to = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[2] + " " + parts[3]));
        rewindTask.accept(from, to);
    }
}
