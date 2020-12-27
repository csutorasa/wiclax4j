package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.request.ByeRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Parser for {@link ByeRequest}.
 */
public class ByeRequestParser implements WiclaxRequestParser {
    private static final String COMMAND = "BYE";

    @Override
    public boolean supports(String command, String data) {
        return COMMAND.equals(command);
    }

    @Override
    public WiclaxRequest parse(String data) {
        return new ByeRequest();
    }
}
