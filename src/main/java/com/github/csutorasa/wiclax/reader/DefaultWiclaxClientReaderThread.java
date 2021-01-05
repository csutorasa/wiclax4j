package com.github.csutorasa.wiclax.reader;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
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

import java.util.function.Consumer;

/**
 * Default reader implementation with its own thread.
 */
public class DefaultWiclaxClientReaderThread extends AbstractWiclaxClientReader {

    private final Consumer<String> unparseableRequest;
    private final Consumer<WiclaxRequest> unhandledRequest;
    private final ErrorHandler<Exception> unhandledRequestErrorHandler;
    private final ErrorHandler<Throwable> threadException;

    /**
     * Creates a new reader.
     *
     * @param rewindHandler rewind request handler
     */
    public DefaultWiclaxClientReaderThread(RewindHandler rewindHandler) {
        super(rewindHandler);
        unparseableRequest = request -> {
        };
        unhandledRequest = request -> {
        };
        unhandledRequestErrorHandler = exception -> {
        };
        threadException = ErrorHandler.rethrow();
    }

    /**
     * Creates a new reader.
     *
     * @param rewindHandler                rewind request handler
     * @param unparseableRequest           unparseable request handler
     * @param unhandledRequest             unhandled request handler
     * @param unhandledRequestErrorHandler unhandled request handler
     * @param threadException              thread exception handler
     */
    public DefaultWiclaxClientReaderThread(RewindHandler rewindHandler, Consumer<String> unparseableRequest,
                                           Consumer<WiclaxRequest> unhandledRequest,
                                           ErrorHandler<Exception> unhandledRequestErrorHandler,
                                           ErrorHandler<Throwable> threadException) {
        super(rewindHandler);
        this.unparseableRequest = unparseableRequest;
        this.unhandledRequest = unhandledRequest;
        this.unhandledRequestErrorHandler = unhandledRequestErrorHandler;
        this.threadException = threadException;
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

    private void unparseableRequest(String request) {
        unparseableRequest.accept(request);
    }

    private void unhandledRequest(WiclaxRequest request) {
        unhandledRequest.accept(request);
    }

    private void unhandledProcessingException(Exception exception) {
        unhandledRequestErrorHandler.handle(exception);
    }

    private void threadException(Throwable throwable) {
        threadException.handle(throwable);
    }
}
