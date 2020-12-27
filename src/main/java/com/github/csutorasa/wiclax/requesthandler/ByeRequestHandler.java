package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.ByeRequest;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * Last message before closing the connection.
 */
public class ByeRequestHandler extends AbstractWiclaxRequestHandler<ByeRequest> {

    /**
     * Creates a new handler.
     */
    public ByeRequestHandler() {
        super(ByeRequest.class);
    }

    @Override
    public WiclaxResponse handleRequest(ByeRequest request) {
        // Connection is closing
        return null;
    }
}
