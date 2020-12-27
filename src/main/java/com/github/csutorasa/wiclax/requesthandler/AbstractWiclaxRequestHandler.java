package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.RequiredArgsConstructor;

/**
 * Base of all request handlers.
 */
@RequiredArgsConstructor
public abstract class AbstractWiclaxRequestHandler<T extends WiclaxRequest> implements WiclaxRequestHandler {

    private final Class<T> clazz;

    @Override
    public boolean supports(WiclaxRequest request) {
        return clazz.isInstance(request);
    }

    @SuppressWarnings("unchecked")
    @Override
    public WiclaxResponse handle(WiclaxRequest request) {
        return handleRequest((T) request);
    }

    /**
     * Handles the request.
     *
     * @param request request data
     * @return response to be sent or null
     */
    protected abstract WiclaxResponse handleRequest(T request);
}
