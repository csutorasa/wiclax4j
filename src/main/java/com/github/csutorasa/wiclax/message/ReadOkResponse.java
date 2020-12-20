package com.github.csutorasa.wiclax.message;

/**
 * Response for marking that reading acquisitions status change was successful.
 */
public class ReadOkResponse extends WiclaxMessage {
    @Override
    public String toData() {
        return "READOK";
    }
}
