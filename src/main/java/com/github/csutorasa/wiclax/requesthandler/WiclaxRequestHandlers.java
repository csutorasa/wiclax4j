package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Collection of request handlers.
 */
public class WiclaxRequestHandlers {

    /**
     * List of handlers
     */
    private final List<WiclaxRequestHandler> handlers;

    /**
     * Creates a new {@link WiclaxRequestHandlers} with all the known handlers.
     *
     * @param connection       client connection
     * @param startReadHandler start read request handler
     * @param stopReadHandler  stop read request handler
     * @param rewindHandler    rewind request handler
     * @return new handlers
     */
    public static WiclaxRequestHandlers fromAllHandlers(WiclaxClientConnection connection, StartReadHandler startReadHandler,
                                                        StopReadHandler stopReadHandler, RewindHandler rewindHandler) {
        List<WiclaxRequestHandler> handlers = new ArrayList<>();
        handlers.add(new ByeRequestHandler());
        handlers.add(new GetClockRequestHandler(connection.getClock()));
        handlers.add(new InitializationRequestHandler());
        handlers.add(new RewindRequestHandler(rewindHandler));
        handlers.add(new SetClockRequestHandler(connection.getClock()));
        handlers.add(new StartReadRequestHandler(startReadHandler));
        handlers.add(new StopReadRequestHandler(stopReadHandler));
        return new WiclaxRequestHandlers(handlers);
    }

    /**
     * Creates a new {@link WiclaxRequestHandlers} with the given handlers.
     *
     * @param handlers list of handlers
     */
    public WiclaxRequestHandlers(Collection<WiclaxRequestHandler> handlers) {
        this.handlers = new ArrayList<>(handlers);
    }

    /**
     * Handles a request.
     *
     * @param request request
     * @return response to be sent or null
     * @throws UnhandledRequestException if no handlers support the request
     */
    public WiclaxResponse handle(WiclaxRequest request) throws UnhandledRequestException {
        WiclaxRequestHandler handler = handlers.stream().filter(h -> h.supports(request)).findFirst().orElseThrow(() ->
                new UnhandledRequestException(request));
        return handler.handle(request);
    }
}
