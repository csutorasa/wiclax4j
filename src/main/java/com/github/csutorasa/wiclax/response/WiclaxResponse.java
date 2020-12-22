package com.github.csutorasa.wiclax.response;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;

/**
 * Base response class.
 */
public interface WiclaxResponse {
    /**
     * Convert message to string that can be sent to Wiclax clients.
     *
     * @return message string
     */
    String toData();
}
