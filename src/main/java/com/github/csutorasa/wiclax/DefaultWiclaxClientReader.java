package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.request.RewindHandler;
import com.github.csutorasa.wiclax.request.StartReadHandler;
import com.github.csutorasa.wiclax.request.StopReadHandler;
import com.github.csutorasa.wiclax.request.WiclaxRequestHandlers;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Default reader implementation. The socket is read until it is closed.
 */
@RequiredArgsConstructor
public class DefaultWiclaxClientReader extends WiclaxClientReader {

    private final RewindHandler rewindHandler;
    private final BiConsumer<String, String> unhandledRequest;
    private final Consumer<Exception> unhandledProcessingException;
    private final Consumer<IOException> streamException;

    /**
     * Creates a new reader.
     * @param rewindHandler rewind request handler
     */
    public DefaultWiclaxClientReader(RewindHandler rewindHandler) {
        this.rewindHandler = rewindHandler;
        unhandledRequest = (command, data) -> {};
        unhandledProcessingException = (exception) -> {};
        streamException = (exception) -> {};
    }

    @Override
    protected void startRead(Socket socket, BufferedReader inputStream, WiclaxClientConnection clientConnection,
                             StartReadHandler startReadHandler, StopReadHandler stopReadHandler) {
        WiclaxRequestHandlers requestHandlers = WiclaxRequestHandlers.fromAllHandlers(startReadHandler, stopReadHandler, this::rewindHandler);
        Thread readerThread = new Thread(() -> reader(socket, inputStream, clientConnection, requestHandlers));
        readerThread.setName("Wiclax input reader for " + socket.getRemoteSocketAddress().toString());
        readerThread.start();
    }

    private void reader(Socket socket, BufferedReader inputStream, WiclaxClientConnection clientConnection,
                        WiclaxRequestHandlers requestHandlers) {
        while (!socket.isClosed()) {
            try {
                while (inputStream.ready()) {
                    String line = inputStream.readLine();
                    processLine((command, data) -> processRequest(clientConnection, requestHandlers, command, data), line);
                }
            } catch (IOException e) {
                streamException(e);
            }
        }
    }

    private void processRequest(WiclaxClientConnection clientConnection, WiclaxRequestHandlers requestHandlers, String command, String data) {
        try {
            requestHandlers.handle(clientConnection, command, data);
        } catch (UnhandledRequestException e) {
            unhandledRequest(e.getCommand(), e.getData());
        } catch (Exception e) {
            unhandledProcessingException(e);
        }
    }

    /**
     * Dispatches the rewind request.
     * @param from rewind from
     * @param to rewind to
     */
    @Override
    protected void rewindHandler(Instant from, Instant to) {
        rewindHandler.accept(from, to);
    }

    /**
     * Dispatches the unhandled request exceptions.
     * @param command request command
     * @param data request data
     */
    protected void unhandledRequest(String command, String data) {
        unhandledRequest.accept(command, data);
    }

    /**
     * Dispatches the unhandled processing exceptions.
     * @param exception any exception that happened during request handling
     */
    protected void unhandledProcessingException(Exception exception) {
        unhandledProcessingException.accept(exception);
    }

    /**
     * Dispatches the underlying stream exceptions.
     * @param exception any exception thrown by the socket or the stream
     */
    protected void streamException(IOException exception) {
        streamException.accept(exception);
    }
}
