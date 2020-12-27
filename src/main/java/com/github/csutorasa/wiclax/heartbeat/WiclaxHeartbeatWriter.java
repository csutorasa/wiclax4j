package com.github.csutorasa.wiclax.heartbeat;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.message.MessageSender;

/**
 * Heartbeat writer base for a Wiclax client.
 */
public interface WiclaxHeartbeatWriter {
    /**
     * Starts writing heartbeat messages.
     *
     * @param clientConnection client connection
     * @param messageSender    message sender function
     */
    void startWrite(WiclaxClientConnection clientConnection, MessageSender messageSender);
}
