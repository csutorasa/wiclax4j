package com.github.csutorasa.wiclax.response;

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
