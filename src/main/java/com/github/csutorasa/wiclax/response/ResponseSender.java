package com.github.csutorasa.wiclax.response;

import java.io.IOException;

/**
 * This can store the implementation to send back a response.
 */
@FunctionalInterface
public interface ResponseSender {

    /**
     * Sends a response.
     *
     * @param response response to send
     * @throws IOException if the sending throws an exception
     */
    void send(WiclaxResponse response) throws IOException;
}
