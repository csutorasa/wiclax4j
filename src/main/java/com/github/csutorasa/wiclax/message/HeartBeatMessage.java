package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;

/**
 * Heartbeat message that can check if the client is alive.
 */
public class HeartBeatMessage extends WiclaxMessage {

    private static final String DEFAULT_COMMAND = "*";

    @Override
    public String toData(WiclaxProtocolOptions protocolOptions) {
        return protocolOptions.get(WiclaxProtocolOptions::getHeartbeatValue).orElse(DEFAULT_COMMAND);
    }
}
