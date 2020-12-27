package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.request.GetClockRequest;
import com.github.csutorasa.wiclax.response.ClockResponse;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

import java.time.Instant;

/**
 * Request to get the current time from the clock.
 */
public class GetClockRequestHandler extends AbstractWiclaxRequestHandler<GetClockRequest> {

    private final WiclaxClock clock;

    /**
     * Creates a new handler.
     *
     * @param clock clock
     */
    public GetClockRequestHandler(WiclaxClock clock) {
        super(GetClockRequest.class);
        this.clock = clock;
    }

    @Override
    public WiclaxResponse handleRequest(GetClockRequest request) {
        Instant dateTime = clock.getDateTime();
        return new ClockResponse(dateTime);
    }
}
