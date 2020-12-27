package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.exception.UnhandledRequestException;
import com.github.csutorasa.wiclax.exception.UnparseableRequestException;
import com.github.csutorasa.wiclax.request.RequestReader;
import com.github.csutorasa.wiclax.request.WiclaxRequest;
import com.github.csutorasa.wiclax.requesthandler.WiclaxRequestHandlers;
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers;
import com.github.csutorasa.wiclax.response.ResponseSender;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

import java.io.IOException;

/**
 * Abstract reader for a Wiclax client. Contains reading and processing logic.
 */
public abstract class AbstractWiclaxClientReader implements WiclaxClientReader {

    /**
     * Reads a new request, processes it and responds if it is needed.
     *
     * @param requestHandlers request handlers
     * @param requestParsers  request parsers
     * @param requestReader   request reader logic
     * @param responseSender  response sender logic
     * @throws UnparseableRequestException if no parser supports the request
     * @throws UnhandledRequestException   if no handler supports the request
     * @throws IOException                 if sending the response fails
     */
    protected void readAndProcessRequest(WiclaxRequestHandlers requestHandlers, WiclaxRequestParsers requestParsers,
                                         RequestReader requestReader, ResponseSender responseSender)
            throws UnparseableRequestException, UnhandledRequestException, IOException {
        String line = requestReader.readRequest();
        if (line == null) {
            return;
        }
        WiclaxRequest request = requestParsers.parse(line);
        WiclaxResponse response = requestHandlers.handle(request);
        if (response != null) {
            responseSender.send(response);
        }
    }
}
