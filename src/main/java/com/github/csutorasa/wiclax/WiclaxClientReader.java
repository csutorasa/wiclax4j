package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.requesthandler.StartReadHandler;
import com.github.csutorasa.wiclax.requesthandler.StopReadHandler;
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers;

import java.time.Instant;
import java.util.function.Consumer;

/**
 * Reader base for a Wiclax client.
 */
public abstract class WiclaxClientReader {

    private static final String DEFAULT_OUT_COMMAND_END_CHARS = "\r";

    /**
     * Reads and parses the line. Executes the processor with the command and data parameters.
     *
     * @param processor      request handler
     * @param requestParsers request parsers
     * @param line           line to be parsed
     */
    protected void processLine(Consumer<WiclaxRequest> processor, WiclaxRequestParsers requestParsers, String line) {
        int indexOfFirstSpace = line.indexOf(" ");
        String command;
        String data;
        if (indexOfFirstSpace < 0) {
            command = line;
            data = "";
        } else {
            command = line.substring(0, indexOfFirstSpace);
            data = line.substring(indexOfFirstSpace + 1);
        }
        WiclaxRequest request = requestParsers.parse(command, data);
        processor.accept(request);
    }

    /**
     * Gets the command separator string.
     *
     * @param protocolOptions protocol options
     * @return separator
     */
    protected String getCommandEndString(WiclaxProtocolOptions protocolOptions) {
        return protocolOptions.get(WiclaxProtocolOptions::getOutCommandEndChars).orElse(DEFAULT_OUT_COMMAND_END_CHARS);
    }

    /**
     * Starts the reading from the client.
     *
     * @param clientConnection connection
     * @param startReadHandler start review handler
     * @param stopReadHandler  stop review handler
     */
    protected abstract void startRead(WiclaxClientConnection clientConnection, StartReadHandler startReadHandler,
                                      StopReadHandler stopReadHandler);

    /**
     * Handles the rewind events. It should replay events between the period.
     *
     * @param from rewind from
     * @param to   rewind to
     */
    protected abstract void rewindHandler(Instant from, Instant to);
}
