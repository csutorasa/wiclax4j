package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.request.StartReadHandler;
import com.github.csutorasa.wiclax.request.StopReadHandler;

import java.io.BufferedReader;
import java.net.Socket;
import java.time.Instant;
import java.util.function.BiConsumer;

/**
 * Reader base for a Wiclax client.
 */
public abstract class WiclaxClientReader {

    /**
     * Reads and parses the line. Executes the processor with the command and data parameters.
     * @param processor command and data handler
     * @param line line to be parsed
     */
    protected void processLine(BiConsumer<String, String> processor, String line) {
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
        processor.accept(command, data);
    }

    /**
     * Starts the reading from the client.
     * @param socket client socket
     * @param inputStream input stream reader
     * @param clientConnection connection
     * @param startReadHandler start review handler
     * @param stopReadHandler stop review handler
     */
    protected abstract void startRead(Socket socket, BufferedReader inputStream, WiclaxClientConnection clientConnection,
                                      StartReadHandler startReadHandler, StopReadHandler stopReadHandler);

    /**
     * Handles the rewind events. It should replay events between the period.
     * @param from rewind from
     * @param to rewind to
     */
    protected abstract void rewindHandler(Instant from, Instant to);
}
