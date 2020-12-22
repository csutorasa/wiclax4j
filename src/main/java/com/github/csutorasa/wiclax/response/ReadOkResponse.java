package com.github.csutorasa.wiclax.response;

/**
 * Response for marking that reading acquisitions status change was successful.
 */
public class ReadOkResponse implements WiclaxResponse {
    @Override
    public String toData() {
        return "READOK";
    }
}
