package com.github.csutorasa.wiclax.exception;

import lombok.Getter;

/**
 * Exception that is thrown when no request parser can parse the request.
 */
@Getter
public class UnparseableRequestException extends Exception {
    /**
     * Request command
     */
    private final String command;
    /**
     * Request data
     */
    private final String data;

    /**
     * Creates a new Exception from the command and data
     *
     * @param command request command
     * @param data    request data
     */
    public UnparseableRequestException(String command, String data) {
        super("Unparseable request: " + command + " " + data);
        this.command = command;
        this.data = data;
    }
}
