package com.github.csutorasa.wiclax.exception;

import com.github.csutorasa.wiclax.request.WiclaxRequest;
import lombok.Getter;

/**
 * Exception that is thrown when no request handler can process the request.
 */
@Getter
public class UnhandledRequestException extends Exception {
    /**
     * Request
     */
    private final WiclaxRequest request;

    /**
     * Creates a new Exception from the request
     *
     * @param request request
     */
    public UnhandledRequestException(WiclaxRequest request) {
        super("Unhandled request: " + request.toString());
        this.request = request;
    }
}
