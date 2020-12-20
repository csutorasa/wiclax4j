package com.github.csutorasa.wiclax.message;

public class ReadOkResponse extends WiclaxMessage {
    @Override
    public String toData() {
        return "READOK";
    }
}
