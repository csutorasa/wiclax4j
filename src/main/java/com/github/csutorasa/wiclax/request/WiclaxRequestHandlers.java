package com.github.csutorasa.wiclax.request;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WiclaxRequestHandlers {

    private final List<WiclaxRequestHandler> handlers;

    public static WiclaxRequestHandlers fromAllHandlers(StartReadHandler startReadHandler, StopReadHandler stopReadHandler,
                                                        RewindHandler rewindHandler) {
        List<WiclaxRequestHandler> handlers = new ArrayList<>();
        handlers.add(new ByeRequestHandler());
        handlers.add(new GetClockRequestHandler());
        handlers.add(new HelloRequestHandler());
        handlers.add(new RewindRequestHandler(rewindHandler));
        handlers.add(new SetClockRequestHandler());
        handlers.add(new StartReadRequestHandler(startReadHandler));
        handlers.add(new StopReadRequestHandler(stopReadHandler));
        return new WiclaxRequestHandlers(handlers);
    }

    public WiclaxRequestHandlers(Collection<WiclaxRequestHandler> handlers) {
        this.handlers = new ArrayList<>(handlers);
    }

    public void handle(WiclaxClientConnection connection, String command, String data) {
        WiclaxRequestHandler handler = handlers.stream().filter(h -> h.supports(command, data)).findFirst().orElseThrow(() ->
                new UnhandledRequestException(command, data));
        handler.handle(connection, data);
    }
}
