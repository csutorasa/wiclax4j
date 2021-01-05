package com.github.csutorasa.wiclax.heartbeat;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.message.MessageSender;

import java.io.IOException;

/**
 * Manual heartbeat writer.
 */
public class ManualWiclaxHeartbeatWriter extends AbstractWiclaxHeartbeatWriter {

    private MessageSender messageSender;

    @Override
    public void startWrite(WiclaxClientConnection clientConnection, MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void send() throws IOException {
        sendHeartbeatMessage(messageSender);
    }
}
