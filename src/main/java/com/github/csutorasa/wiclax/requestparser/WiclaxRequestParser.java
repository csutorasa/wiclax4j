package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.request.WiclaxRequest;

/**
 * Base request parser class.
 */
public interface WiclaxRequestParser {

    /**
     * Parses the request.
     *
     * @param request request line
     * @return request object or null if not supported
     */
    WiclaxRequest parse(String request);
}
