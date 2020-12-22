package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;

/**
 * Base class of messages that can be sent to Wiclax clients.
 */
public abstract class WiclaxMessage {
    /**
     * Convert message to string that can be sent to Wiclax clients.
     *
     * @param protocolOptions protocol options
     * @return message string
     */
    public abstract String toData(WiclaxProtocolOptions protocolOptions);
}
