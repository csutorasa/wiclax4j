package com.github.csutorasa.wiclax.message;

/**
 * Base class of messages that can be sent to Wiclax clients.
 */
public abstract class WiclaxMessage {
    /**
     * Convert message to string that can be sent to Wiclax clients.
     * @return message string
     */
    public abstract String toData();

    @Override
    public String toString() {
        return toData();
    }
}
