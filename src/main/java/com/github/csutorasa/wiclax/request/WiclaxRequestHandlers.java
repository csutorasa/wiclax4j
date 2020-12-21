package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;

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
     * @param protocolOptions  protocol options
     * @param startReadHandler start read request handler
     * @param stopReadHandler  stop read request handler
     * @param rewindHandler    rewind request handler
     * @return new handlers
     */
    public static WiclaxRequestHandlers fromAllHandlers(WiclaxProtocolOptions protocolOptions, StartReadHandler startReadHandler,
                                                        StopReadHandler stopReadHandler, RewindHandler rewindHandler) {
        List<WiclaxRequestHandler> handlers = new ArrayList<>();
        handlers.add(new ByeRequestHandler());
        handlers.add(new GetClockRequestHandler(protocolOptions));
        handlers.add(new InitializationRequestHandler(protocolOptions));
        handlers.add(new RewindRequestHandler(protocolOptions, rewindHandler));
        handlers.add(new SetClockRequestHandler(protocolOptions));
        handlers.add(new StartReadRequestHandler(protocolOptions, startReadHandler));
        handlers.add(new StopReadRequestHandler(protocolOptions, stopReadHandler));
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
     * @param connection client connection
     * @param command    request command
     * @param data       request data
     */
    public void handle(WiclaxClientConnection connection, String command, String data) {
        WiclaxRequestHandler handler = handlers.stream().filter(h -> h.supports(command, data)).findFirst().orElseThrow(() ->
                new UnhandledRequestException(command, data));
        handler.handle(connection, data);
    }
}
