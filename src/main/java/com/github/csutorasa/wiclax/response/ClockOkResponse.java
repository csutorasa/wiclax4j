package com.github.csutorasa.wiclax.response;

/**
 * Response to successful clock setting.
 */
public class ClockOkResponse implements WiclaxResponse {
    @Override
    public String toData() {
        return "CLOCKOK";
    }
}
