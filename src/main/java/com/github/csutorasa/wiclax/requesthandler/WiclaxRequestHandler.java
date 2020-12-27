package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * Base of all request handlers.
 */
public interface WiclaxRequestHandler {
    /**
     * Gets if the handler support the request command and data.
     *
     * @param request request data
     * @return true if it is supported
     */
    boolean supports(WiclaxRequest request);

    /**
     * Handles the request.
     *
     * @param request request data
     * @return response to be sent or null
     */
    WiclaxResponse handle(WiclaxRequest request);
}
