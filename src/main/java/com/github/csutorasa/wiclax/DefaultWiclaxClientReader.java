package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.exception.ErrorHandler;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.request.RewindHandler;
import com.github.csutorasa.wiclax.request.StartReadHandler;
import com.github.csutorasa.wiclax.request.StopReadHandler;
import com.github.csutorasa.wiclax.request.WiclaxRequestHandlers;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.net.Socket;
import java.time.Instant;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * Default reader implementation. The socket is read until it is closed.
 */
@RequiredArgsConstructor
public class DefaultWiclaxClientReader extends WiclaxClientReader {

    private final RewindHandler rewindHandler;
    private final BiConsumer<String, String> unhandledRequest;
    private final ErrorHandler<Exception> unhandledProcessingException;
    private final ErrorHandler<Throwable> threadException;

    /**
     * Creates a new reader.
     *
     * @param rewindHandler rewind request handler
     */
    public DefaultWiclaxClientReader(RewindHandler rewindHandler) {
        this.rewindHandler = rewindHandler;
        unhandledRequest = (command, data) -> {
        };
        unhandledProcessingException = (exception) -> {
        };
        threadException = ErrorHandler.rethrow();
    }

    @Override
    protected void startRead(Socket socket, BufferedReader inputStream, WiclaxClientConnection clientConnection,
                             WiclaxProtocolOptions protocolOptions, StartReadHandler startReadHandler,
                             StopReadHandler stopReadHandler) {
        WiclaxRequestHandlers requestHandlers = WiclaxRequestHandlers.fromAllHandlers(protocolOptions,
                startReadHandler, stopReadHandler, this::rewindHandler);
        Thread readerThread = new Thread(() -> reader(socket, inputStream, protocolOptions, clientConnection, requestHandlers));
        readerThread.setName("Wiclax input reader for " + socket.getRemoteSocketAddress().toString());
        readerThread.start();
    }

    private void reader(Socket socket, BufferedReader inputStream, WiclaxProtocolOptions protocolOptions,
                        WiclaxClientConnection clientConnection, WiclaxRequestHandlers requestHandlers) {
        Scanner scanner = new Scanner(inputStream);
        String delimiter = protocolOptions.get(WiclaxProtocolOptions::getOutCommandEndChars).orElse(DEFAULT_OUT_COMMAND_END_CHARS);
        scanner.useDelimiter(Pattern.compile(delimiter));
        while (!socket.isClosed()) {
            try {
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    processLine((command, data) -> processRequest(clientConnection, requestHandlers, command, data), line);
                }
            } catch (Throwable t) {
                threadException(t);
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
     *
     * @param from rewind from
     * @param to   rewind to
     */
    @Override
    protected void rewindHandler(Instant from, Instant to) {
        rewindHandler.accept(from, to);
    }

    /**
     * Dispatches the unhandled request exceptions.
     *
     * @param command request command
     * @param data    request data
     */
    protected void unhandledRequest(String command, String data) {
        unhandledRequest.accept(command, data);
    }

    /**
     * Dispatches the unhandled processing exceptions.
     *
     * @param exception any exception that happened during request handling
     */
    protected void unhandledProcessingException(Exception exception) {
        unhandledProcessingException.handle(exception);
    }

    /**
     * Last chance to stop the reader thread from being stopped.
     *
     * @param throwable any exception thrown by the thread
     */
    protected void threadException(Throwable throwable) {
        threadException.handle(throwable);
    }
}
