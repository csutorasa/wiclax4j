package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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
     * @param responseSender   response sender
     * @param startReadHandler start read request handler
     * @param stopReadHandler  stop read request handler
     * @param rewindHandler    rewind request handler
     * @return new handlers
     */
    public static WiclaxRequestHandlers fromAllHandlers(WiclaxClientConnection connection, ResponseSender responseSender,
                                                        StartReadHandler startReadHandler, StopReadHandler stopReadHandler,
                                                        RewindHandler rewindHandler) {
        List<WiclaxRequestHandler> handlers = new ArrayList<>();
        handlers.add(new ByeRequestHandler());
        handlers.add(new GetClockRequestHandler(connection.getProtocolOptions(), connection.getClock(), responseSender));
        handlers.add(new InitializationRequestHandler(connection.getProtocolOptions()));
        handlers.add(new RewindRequestHandler(connection.getProtocolOptions(), rewindHandler));
        handlers.add(new SetClockRequestHandler(connection.getProtocolOptions(), connection.getClock(), responseSender));
        handlers.add(new StartReadRequestHandler(connection.getProtocolOptions(), startReadHandler, responseSender));
        handlers.add(new StopReadRequestHandler(connection.getProtocolOptions(), stopReadHandler, responseSender));
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
     * @param command    request command
     * @param data       request data
     */
    public void handle(String command, String data) {
        WiclaxRequestHandler handler = handlers.stream().filter(h -> h.supports(command, data)).findFirst().orElseThrow(() ->
                new UnhandledRequestException(command, data));
        handler.handle(data);
    }
}
