package com.github.csutorasa.wiclax.exception;

import lombok.Getter;

/**
 * Exception that is thrown when no request handler can process the request.
 */
@Getter
public class UnhandledRequestException extends RuntimeException {
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
     * @param command request command
     * @param data request data
     */
    public UnhandledRequestException(String command, String data) {
        super("Unhandled request: " + command + " " + data);
        this.command = command;
        this.data = data;
    }
}
