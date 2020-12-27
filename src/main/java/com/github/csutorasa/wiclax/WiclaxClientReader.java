package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.request.RequestReader;
import com.github.csutorasa.wiclax.requesthandler.StartReadHandler;
import com.github.csutorasa.wiclax.requesthandler.StopReadHandler;
import com.github.csutorasa.wiclax.response.ResponseSender;

import java.time.Instant;

/**
 * Base reader for a Wiclax client.
 */
public interface WiclaxClientReader {

    /**
     * Starts the reading from the client.
     *
     * @param clientConnection connection
     * @param requestReader    request reader logic
     * @param responseSender   response sender logic
     * @param startReadHandler start review handler
     * @param stopReadHandler  stop review handler
     */
    void startRead(WiclaxClientConnection clientConnection, RequestReader requestReader, ResponseSender responseSender,
                   StartReadHandler startReadHandler, StopReadHandler stopReadHandler);

    /**
     * Handles the rewind events. It should replay events between the period.
     *
     * @param from rewind from
     * @param to   rewind to
     */
    void rewindHandler(Instant from, Instant to);
}
