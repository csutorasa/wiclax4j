package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.exception.ErrorHandler;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.exception.UnparseableRequestException;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.requesthandler.RewindHandler;
import com.github.csutorasa.wiclax.requesthandler.StartReadHandler;
import com.github.csutorasa.wiclax.requesthandler.StopReadHandler;
import com.github.csutorasa.wiclax.requesthandler.WiclaxRequestHandlers;
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers;
import com.github.csutorasa.wiclax.response.ResponseSender;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.RequiredArgsConstructor;

import java.io.Reader;
import java.net.Socket;
import java.time.Instant;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Default reader implementation. The socket is read until it is closed.
 */
@RequiredArgsConstructor
public class DefaultWiclaxClientReader extends WiclaxClientReader {

    private final RewindHandler rewindHandler;
    private final BiConsumer<String, String> unparseableRequest;
    private final Consumer<WiclaxRequest> unhandledRequest;
    private final ErrorHandler<Exception> unhandledRequestErrorHandler;
    private final ErrorHandler<Throwable> threadException;

    /**
     * Creates a new reader.
     *
     * @param rewindHandler rewind request handler
     */
    public DefaultWiclaxClientReader(RewindHandler rewindHandler) {
        this.rewindHandler = rewindHandler;
        unparseableRequest = (command, data) -> {
        };
        unhandledRequest = request -> {
        };
        unhandledRequestErrorHandler = exception -> {
        };
        threadException = ErrorHandler.rethrow();
    }

    @Override
    protected void startRead(WiclaxClientConnection clientConnection, StartReadHandler startReadHandler,
                             StopReadHandler stopReadHandler) {
        Socket socket = clientConnection.getSocket();
        Reader inputStream = clientConnection.getInputStream();
        WiclaxRequestHandlers requestHandlers = WiclaxRequestHandlers.fromAllHandlers(clientConnection,
                startReadHandler, stopReadHandler, this::rewindHandler);
        WiclaxRequestParsers requestParsers = WiclaxRequestParsers.fromAllParsers(clientConnection);
        Thread readerThread = new Thread(() -> reader(socket, inputStream, clientConnection.getProtocolOptions(),
                requestHandlers, requestParsers, clientConnection::send));
        readerThread.setName("Wiclax input reader for " + socket.getRemoteSocketAddress().toString());
        readerThread.start();
    }

    private void reader(Socket socket, Reader inputStream, WiclaxProtocolOptions protocolOptions,
                        WiclaxRequestHandlers requestHandlers, WiclaxRequestParsers requestParsers,
                        ResponseSender responseSender) {
        Scanner scanner = new Scanner(inputStream);
        String delimiter = getCommandEndString(protocolOptions);
        scanner.useDelimiter(Pattern.compile(delimiter));
        while (!socket.isClosed()) {
            try {
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    processLine(request -> processRequest(requestHandlers, responseSender, request), requestParsers, line);
                }
            } catch (Throwable t) {
                threadException(t);
            }
        }
    }

    private void processRequest(WiclaxRequestHandlers requestHandlers, ResponseSender responseSender, WiclaxRequest request) {
        try {
            WiclaxResponse response = requestHandlers.handle(request);
            if (response != null) {
                responseSender.send(response);
            }
        } catch (UnparseableRequestException e) {
            unparseableRequest(e.getCommand(), e.getData());
        } catch (UnhandledRequestException e) {
            unhandledRequest(e.getRequest());
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
     * Dispatches the unparseable request exceptions.
     *
     * @param command request command
     * @param data    request data
     */
    protected void unparseableRequest(String command, String data) {
        unparseableRequest.accept(command, data);
    }

    /**
     * Dispatches the unhandled request exceptions.
     *
     * @param request request
     */
    protected void unhandledRequest(WiclaxRequest request) {
        unhandledRequest.accept(request);
    }

    /**
     * Dispatches the unhandled processing exceptions.
     *
     * @param exception any exception that happened during request handling
     */
    protected void unhandledProcessingException(Exception exception) {
        unhandledRequestErrorHandler.handle(exception);
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
