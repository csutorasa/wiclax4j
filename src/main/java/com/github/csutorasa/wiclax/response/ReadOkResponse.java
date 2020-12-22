package com.github.csutorasa.wiclax.response;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.WiclaxMessage;

/**
 * Response for marking that reading acquisitions status change was successful.
 */
public class ReadOkResponse implements WiclaxResponse {
    @Override
    public String toData() {
        return "READOK";
    }
}
