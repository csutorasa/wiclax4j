package com.github.csutorasa.wiclax.message;

public class ClockOkResponse extends WiclaxMessage {
    @Override
    public String toData() {
        return "CLOCKOK";
    }
}
