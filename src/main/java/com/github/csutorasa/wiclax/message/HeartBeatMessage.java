package com.github.csutorasa.wiclax.message;

public class HeartBeatMessage extends WiclaxMessage {
    @Override
    public String toData() {
        return "*";
    }
}
