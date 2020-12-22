package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * This can store the implementation to send back a response.
 */
@FunctionalInterface
public interface ResponseSender {

    /**
     * Sends a response.
     * @param response response to send
     */
    void send(WiclaxResponse response);
}
