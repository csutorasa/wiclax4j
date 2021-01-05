package com.github.csutorasa.wiclax.reader;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.request.RequestReader;
import com.github.csutorasa.wiclax.requesthandler.StartReadHandler;
import com.github.csutorasa.wiclax.requesthandler.StopReadHandler;
import com.github.csutorasa.wiclax.response.ResponseSender;

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
}
