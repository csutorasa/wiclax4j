package com.github.csutorasa.wiclax.reader;

import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.exception.UnparseableRequestException;
import com.github.csutorasa.wiclax.request.RequestReader;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.requesthandler.RewindHandler;
import com.github.csutorasa.wiclax.requesthandler.WiclaxRequestHandlers;
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers;
import com.github.csutorasa.wiclax.response.ResponseSender;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.Instant;

/**
 * Abstract reader for a Wiclax client. Contains reading and processing logic.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractWiclaxClientReader implements WiclaxClientReader {

    private final RewindHandler rewindHandler;

    /**
     * Reads a new request.
     *
     * @param requestParsers request parsers
     * @param requestReader  request reader logic
     * @return parsed request
     * @throws UnparseableRequestException if no parser supports the request
     */
    protected WiclaxRequest read(WiclaxRequestParsers requestParsers, RequestReader requestReader) throws UnparseableRequestException {
        String line = requestReader.readRequest();
        if (line == null) {
            return null;
        }
        return requestParsers.parse(line);
    }

    /**
     * Processes a request and responds if it is needed.
     *
     * @param requestHandlers request handlers
     * @param responseSender  response sender logic
     * @param request         parsed request
     * @throws UnhandledRequestException if no handler supports the request
     * @throws IOException               if sending the response fails
     */
    protected void processAndRespond(WiclaxRequestHandlers requestHandlers, ResponseSender responseSender,
                                     WiclaxRequest request) throws UnhandledRequestException, IOException {
        WiclaxResponse response = requestHandlers.handle(request);
        if (response != null) {
            responseSender.send(response);
        }
    }

    /**
     * Reads a new request, processes it and responds if it is needed.
     *
     * @param requestHandlers request handlers
     * @param requestParsers  request parsers
     * @param requestReader   request reader logic
     * @param responseSender  response sender logic
     * @return true if request was read
     * @throws UnparseableRequestException if no parser supports the request
     * @throws UnhandledRequestException   if no handler supports the request
     * @throws IOException                 if sending the response fails
     */
    protected boolean readAndProcessRequest(WiclaxRequestHandlers requestHandlers, WiclaxRequestParsers requestParsers,
                                         RequestReader requestReader, ResponseSender responseSender)
            throws UnparseableRequestException, UnhandledRequestException, IOException {
        WiclaxRequest request = read(requestParsers, requestReader);
        if (request != null) {
            processAndRespond(requestHandlers, responseSender, request);
            return true;
        }
        return false;
    }

    /**
     * Handles the rewind events. It should replay events between the period.
     *
     * @param from rewind from
     * @param to   rewind to
     */
    protected void rewindHandler(Instant from, Instant to) {
        rewindHandler.accept(from, to);
    }
}
