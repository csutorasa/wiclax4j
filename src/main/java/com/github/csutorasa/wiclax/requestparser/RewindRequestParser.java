package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.request.RewindRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Parser for {@link RewindRequest}.
 */
@RequiredArgsConstructor
public class RewindRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "REWIND";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public WiclaxRequest parse(String request) {
        Optional<String> rewindCommand = protocolOptions.get(WiclaxProtocolOptions::getRewindCommand);
        if (rewindCommand.isPresent()) {
            try {
                DateTimeFormatter formatter = WiclaxDateFormatters.createFormWiclaxPattern2(rewindCommand.get());
                return null;
            } catch (Exception e) {
                return null;
            }
        } else {
            String[] parts = request.split(" ");
            if (DEFAULT_COMMAND.equals(parts[0]) && parts.length == 5) {
                Instant from = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[1] + " " + parts[2]));
                Instant to = Instant.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse(parts[3] + " " + parts[4]));
                return new RewindRequest(from, to);
            } else {
                return null;
            }
        }
    }
}
