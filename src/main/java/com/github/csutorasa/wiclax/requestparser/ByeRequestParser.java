package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.request.ByeRequest;
import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Parser for {@link ByeRequest}.
 */
public class ByeRequestParser implements WiclaxRequestParser {
    private static final String COMMAND = "BYE";

    @Override
    public WiclaxRequest parse(String request) {
        if (COMMAND.equals(request)) {
            return new ByeRequest();
        }
        return null;
    }
}
