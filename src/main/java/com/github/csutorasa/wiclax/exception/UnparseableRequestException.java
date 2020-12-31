package com.github.csutorasa.wiclax.exception;

import lombok.Getter;

/**
 * Exception that is thrown when no request parser can parse the request.
 */
@Getter
public class UnparseableRequestException extends Exception {

    /**
     * Request line
     */
    private final String requestLine;

    /**
     * Creates a new Exception from the request line
     *
     * @param requestLine request line
     */
    public UnparseableRequestException(String requestLine) {
        super("Unparseable request: " + requestLine);
        this.requestLine = requestLine;
    }
}
