package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.message.MessageSender;

/**
 * Heartbeat writer base for a Wiclax client.
 */
public abstract class WiclaxHeartbeatWriter {
    /**
     * Starts writing heartbeat messages.
     *
     * @param clientConnection client connection
     * @param messageSender    message sender function
     */
    public abstract void startWrite(WiclaxClientConnection clientConnection, MessageSender messageSender);
}
