package com.github.csutorasa.wiclax.heartbeat;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.message.MessageSender;

import java.io.IOException;

/**
 * Default heartbeat writer.
 */
public class DefaultWiclaxHeartbeatWriter extends AbstractWiclaxHeartbeatWriter {

    private MessageSender messageSender;

    @Override
    public void startWrite(WiclaxClientConnection clientConnection, MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Sends a heartbeat message.
     *
     * @throws IOException if the sending throws an exception
     */
    public void send() throws IOException {
        sendHeartbeatMessage(messageSender);
    }
}
