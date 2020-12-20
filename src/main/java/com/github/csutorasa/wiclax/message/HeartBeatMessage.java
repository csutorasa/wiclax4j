package com.github.csutorasa.wiclax.message;

/**
 * Heartbeat message that can check if the client is alive.
 */
public class HeartBeatMessage extends WiclaxMessage {
    @Override
    public String toData() {
        return "*";
    }
}
