package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.InitializationRequest;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * First message of the connection. Requires no actions.
 */
public class InitializationRequestHandler extends AbstractWiclaxRequestHandler<InitializationRequest> {

    /**
     * Creates a new handler.
     */
    public InitializationRequestHandler() {
        super(InitializationRequest.class);
    }

    @Override
    public WiclaxResponse handleRequest(InitializationRequest request) {
        // Connection is up
        return null;
    }
}
