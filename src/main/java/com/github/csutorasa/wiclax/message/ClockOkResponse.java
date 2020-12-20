package com.github.csutorasa.wiclax.message;

/**
 * Response to successful clock setting.
 */
public class ClockOkResponse extends WiclaxMessage {
    @Override
    public String toData() {
        return "CLOCKOK";
    }
}
