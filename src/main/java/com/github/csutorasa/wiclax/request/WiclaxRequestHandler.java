package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.message.WiclaxMessage;

import java.io.IOException;

/**
 * Base of all request handlers.
 */
public abstract class WiclaxRequestHandler {
    /**
     * Gets if the handler support the request command and data.
     * @param command request command
     * @param data request data
     * @return true if it is supported
     */
    public abstract boolean supports(String command, String data);

    /**
     * Handles the request.
     * @param clientConnection connection where the data originates
     * @param data request data
     */
    public abstract void handle(WiclaxClientConnection clientConnection, String data);

    /**
     * Sends data to Wiclax and suppresses exceptions.
     * @param clientConnection connection to send data to
     * @param message response message
     */
    protected void send(WiclaxClientConnection clientConnection, WiclaxMessage message) {
        try {
            clientConnection.send(message);
        } catch (IOException e) {
            // Suppressed
        }
    }
}
