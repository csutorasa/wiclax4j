package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxDateFormatters;
import com.github.csutorasa.wiclax.request.SetClockRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Parser for {@link SetClockRequest}.
 */
@RequiredArgsConstructor
public class SetClockRequestParser implements WiclaxRequestParser {
    private static final String DEFAULT_COMMAND = "CLOCK";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public WiclaxRequest parse(String request) {
        Optional<String> setClockCommand = protocolOptions.get(WiclaxProtocolOptions::getSetClockCommand);
        if (setClockCommand.isPresent()) {
            try {
                DateTimeFormatter formatter = WiclaxDateFormatters.createFormWiclaxPattern2(setClockCommand.get());
                return fromFormatterAndData(formatter, request);
            } catch (Exception e) {
                return null;
            }
        } else {
            if (request.startsWith(DEFAULT_COMMAND + " ")) {
                String data = request.substring(request.indexOf(" ") + 1);
                return fromFormatterAndData(WiclaxDateFormatters.DATE_TIME_FORMATTER, data);
            } else {
                return null;
            }
        }
    }

    private SetClockRequest fromFormatterAndData(DateTimeFormatter formatter, String data) {
        Instant dateTime = Instant.from(formatter.parse(data));
        return new SetClockRequest(dateTime);
    }
}
