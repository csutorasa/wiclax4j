package com.github.csutorasa.wiclax.requestparser;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.exception.UnparseableRequestException;
import com.github.csutorasa.wiclax.request.WiclaxRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Collection of request parsers.
 */
public class WiclaxRequestParsers {

    private final List<WiclaxRequestParser> parsers;

    /**
     * Creates a new {@link WiclaxRequestParsers} with all the known parsers.
     *
     * @param connection client connection
     * @return new handlers
     */
    public static WiclaxRequestParsers fromAllParsers(WiclaxClientConnection connection) {
        List<WiclaxRequestParser> parsers = new ArrayList<>();
        parsers.add(new ByeRequestParser());
        parsers.add(new GetClockRequestParser(connection.getProtocolOptions()));
        parsers.add(new InitializationRequestParser(connection.getProtocolOptions()));
        parsers.add(new RewindRequestParser(connection.getProtocolOptions()));
        parsers.add(new SetClockRequestParser(connection.getProtocolOptions()));
        parsers.add(new StartReadRequestParser(connection.getProtocolOptions()));
        parsers.add(new StopReadRequestParser(connection.getProtocolOptions()));
        return new WiclaxRequestParsers(parsers);
    }

    /**
     * Creates a new {@link WiclaxRequestParsers} with the given parsers.
     *
     * @param parsers list of parsers
     */
    public WiclaxRequestParsers(Collection<WiclaxRequestParser> parsers) {
        this.parsers = new ArrayList<>(parsers);
    }

    /**
     * Parses a request.
     *
     * @param command request command
     * @param data    request data
     * @return request object
     */
    public WiclaxRequest parse(String command, String data) {
        WiclaxRequestParser parser = parsers.stream().filter(h -> h.supports(command, data)).findFirst().orElseThrow(() ->
                new UnparseableRequestException(command, data));
        return parser.parse(data);
    }
}
