package com.github.csutorasa.wiclax.reader;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
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

import java.io.IOException;

/**
 * Default reader implementation.
 */
public class DefaultWiclaxClientReader extends AbstractWiclaxClientReader {

    private WiclaxRequestHandlers requestHandlers;
    private WiclaxRequestParsers requestParsers;
    private RequestReader requestReader;
    private ResponseSender responseSender;

    /**
     * Creates a new reader.
     *
     * @param rewindHandler rewind request handler
     */
    public DefaultWiclaxClientReader(RewindHandler rewindHandler) {
        super(rewindHandler);
    }

    @Override
    public void startRead(WiclaxClientConnection clientConnection, RequestReader requestReader,
                          ResponseSender responseSender, StartReadHandler startReadHandler,
                          StopReadHandler stopReadHandler) {
        requestHandlers = WiclaxRequestHandlers.fromAllHandlers(clientConnection,
                startReadHandler, stopReadHandler, this::rewindHandler);
        requestParsers = WiclaxRequestParsers.fromAllParsers(clientConnection);
        this.requestReader = requestReader;
        this.responseSender = responseSender;
    }

    /**
     * Reads a new request.
     *
     * @return parsed request
     * @throws UnparseableRequestException if no parser supports the request
     */
    public WiclaxRequest readRequest() throws UnparseableRequestException {
        return read(requestParsers, requestReader);
    }

    /**
     * Processes a request and responds if it is needed.
     *
     * @param request parsed request
     * @throws UnhandledRequestException if no handler supports the request
     * @throws IOException               if sending the response fails
     */
    public void processAndRespond(WiclaxRequest request) throws UnhandledRequestException, IOException {
        processAndRespond(requestHandlers, responseSender, request);
    }

    /**
     * Reads a new request, processes it and responds if it is needed.
     *
     * @throws UnparseableRequestException if no parser supports the request
     * @throws UnhandledRequestException   if no handler supports the request
     * @throws IOException                 if sending the response fails
     */
    public void readAndProcess() throws UnparseableRequestException, IOException, UnhandledRequestException {
        readAndProcessRequest(requestHandlers, requestParsers, requestReader, responseSender);
    }
}
