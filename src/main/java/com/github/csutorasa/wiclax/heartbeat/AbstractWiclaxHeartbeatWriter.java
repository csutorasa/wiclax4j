package com.github.csutorasa.wiclax.heartbeat;

import com.github.csutorasa.wiclax.message.HeartBeatMessage;
import com.github.csutorasa.wiclax.message.MessageSender;

import java.io.IOException;

/**
 * Common base of heartbeat writers.
 */
public abstract class AbstractWiclaxHeartbeatWriter implements WiclaxHeartbeatWriter {
    /**
     * Sends a new heartbeat message.
     *
     * @param messageSender message sender logic
     * @throws IOException if the sending throws an exception
     */
    protected void sendHeartbeatMessage(MessageSender messageSender) throws IOException {
        messageSender.send(new HeartBeatMessage());
    }
}
