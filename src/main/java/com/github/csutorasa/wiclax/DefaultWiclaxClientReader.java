package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.exception.ErrorHandler;
import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.exception.UnparseableRequestException;
import com.github.csutorasa.wiclax.request.RequestReader;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.requesthandler.RewindHandler;
import com.github.csutorasa.wiclax.requesthandler.StartReadHandler;
import com.github.csutorasa.wiclax.requesthandler.StopReadHandler;
import com.github.csutorasa.wiclax.requesthandler.WiclaxRequestHandlers;
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers;
import com.github.csutorasa.wiclax.response.ResponseSender;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.function.Consumer;

/**
 * Default reader implementation. The socket is read until it is closed.
 */
@RequiredArgsConstructor
public class DefaultWiclaxClientReader extends AbstractWiclaxClientReader {

    private final RewindHandler rewindHandler;
    private final Consumer<String> unparseableRequest;
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
        unparseableRequest = request -> {
        };
        unhandledRequest = request -> {
        };
        unhandledRequestErrorHandler = exception -> {
        };
        threadException = ErrorHandler.rethrow();
    }

    @Override
    public void startRead(WiclaxClientConnection clientConnection, RequestReader requestReader,
                          ResponseSender responseSender, StartReadHandler startReadHandler,
                          StopReadHandler stopReadHandler) {
        WiclaxRequestHandlers requestHandlers = WiclaxRequestHandlers.fromAllHandlers(clientConnection,
                startReadHandler, stopReadHandler, this::rewindHandler);
        WiclaxRequestParsers requestParsers = WiclaxRequestParsers.fromAllParsers(clientConnection);
        Thread readerThread = new Thread(() -> reader(requestHandlers, requestParsers, requestReader, responseSender));
        readerThread.setName("Wiclax input reader for " + clientConnection.getRemoteSocketAddress().toString());
        readerThread.start();
    }

    private void reader(WiclaxRequestHandlers requestHandlers, WiclaxRequestParsers requestParsers,
                        RequestReader requestReader, ResponseSender responseSender) {
        boolean exit = false;
        while (!exit) {
            exit = readAndProcess(requestHandlers, requestParsers, requestReader, responseSender);
        }
    }

    private boolean readAndProcess(WiclaxRequestHandlers requestHandlers, WiclaxRequestParsers requestParsers,
                                   RequestReader requestReader, ResponseSender responseSender) {
        try {
            processRequest(requestHandlers, requestParsers, requestReader, responseSender);
        } catch (Throwable t) {
            threadException(t);
        }
        return false;
    }

    private void processRequest(WiclaxRequestHandlers requestHandlers, WiclaxRequestParsers requestParsers,
                                RequestReader requestReader, ResponseSender responseSender) {
        try {
            readAndProcessRequest(requestHandlers, requestParsers, requestReader, responseSender);
        } catch (UnparseableRequestException e) {
            unparseableRequest(e.getRequestLine());
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
    public void rewindHandler(Instant from, Instant to) {
        rewindHandler.accept(from, to);
    }

    /**
     * Dispatches the unparseable request exceptions.
     *
     * @param request request line
     */
    protected void unparseableRequest(String request) {
        unparseableRequest.accept(request);
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
