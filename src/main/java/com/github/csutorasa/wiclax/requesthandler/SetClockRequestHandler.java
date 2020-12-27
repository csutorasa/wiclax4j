package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.request.SetClockRequest;
import com.github.csutorasa.wiclax.response.ClockOkResponse;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * Request to set the clock time.
 */
public class SetClockRequestHandler extends AbstractWiclaxRequestHandler<SetClockRequest> {
    private final WiclaxClock clock;

    /**
     * Creates a new handler.
     *
     * @param clock clock
     */
    public SetClockRequestHandler(WiclaxClock clock) {
        super(SetClockRequest.class);
        this.clock = clock;
    }

    @Override
    public WiclaxResponse handleRequest(SetClockRequest request) {
        clock.setDateTime(request.getTime());
        return new ClockOkResponse();
    }
}
