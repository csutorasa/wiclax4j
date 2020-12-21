package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import lombok.RequiredArgsConstructor;

/**
 * Heartbeat message that can check if the client is alive.
 */
@RequiredArgsConstructor
public class HeartBeatMessage extends WiclaxMessage {

    private static final String DEFAULT_COMMAND = "*";

    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public String toData() {
        return protocolOptions.get(WiclaxProtocolOptions::getHeartbeatValue).orElse(DEFAULT_COMMAND);
    }
}
