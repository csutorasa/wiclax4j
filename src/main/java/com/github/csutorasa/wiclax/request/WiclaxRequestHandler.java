package com.github.csutorasa.wiclax.request;

/**
 * Base of all request handlers.
 */
public interface WiclaxRequestHandler {
    /**
     * Gets if the handler support the request command and data.
     *
     * @param command request command
     * @param data    request data
     * @return true if it is supported
     */
    boolean supports(String command, String data);

    /**
     * Handles the request.
     *
     * @param data request data
     */
    void handle(String data);
}
