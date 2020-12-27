package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Base request parser class.
 */
public interface WiclaxRequestParser {
    /**
     * Gets if the command and the data is supported by this parser.
     *
     * @param command request command
     * @param data    request data
     * @return true if it is supported
     */
    boolean supports(String command, String data);

    /**
     * Parses the request
     *
     * @param data request data
     * @return request object
     */
    WiclaxRequest parse(String data);
}
