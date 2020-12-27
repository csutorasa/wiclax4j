package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.StartReadRequest;
import com.github.csutorasa.wiclax.response.ReadOkResponse;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * Handles the start read event and responds with success.
 */
public class StartReadRequestHandler extends AbstractWiclaxRequestHandler<StartReadRequest> {
    private final StartReadHandler handler;

    /**
     * Creates a new handler.
     *
     * @param handler task
     */
    public StartReadRequestHandler(StartReadHandler handler) {
        super(StartReadRequest.class);
        this.handler = handler;
    }

    @Override
    public WiclaxResponse handleRequest(StartReadRequest request) {
        handler.run();
        return new ReadOkResponse();
    }
}
