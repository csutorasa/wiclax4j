package com.github.csutorasa.wiclax.response;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.WiclaxMessage;

/**
 * Response to successful clock setting.
 */
public class ClockOkResponse implements WiclaxResponse {
    @Override
    public String toData() {
        return "CLOCKOK";
    }
}
