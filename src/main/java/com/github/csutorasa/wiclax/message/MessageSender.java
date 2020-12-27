package com.github.csutorasa.wiclax.message;

import java.io.IOException;

/**
 * This can store the implementation to send a message.
 */
@FunctionalInterface
public interface MessageSender {

    /**
     * Sends a message.
     *
     * @param message message to send
     * @throws IOException if the sending throws an exception
     */
    void send(WiclaxMessage message) throws IOException;
}
