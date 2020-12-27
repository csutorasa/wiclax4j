package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.StopReadRequest;
import com.github.csutorasa.wiclax.response.ReadOkResponse;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * Handles the stop read event and responds with success.
 */
public class StopReadRequestHandler extends AbstractWiclaxRequestHandler<StopReadRequest> {
    private final StopReadHandler handler;

    /**
     * Creates a new handler.
     *
     * @param handler task
     */
    public StopReadRequestHandler(StopReadHandler handler) {
        super(StopReadRequest.class);
        this.handler = handler;
    }

    @Override
    public WiclaxResponse handleRequest(StopReadRequest request) {
        handler.run();
        return new ReadOkResponse();
    }
}
