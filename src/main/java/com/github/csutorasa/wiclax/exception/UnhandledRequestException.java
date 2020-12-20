package com.github.csutorasa.wiclax.exception;

import lombok.Getter;

@Getter
public class UnhandledRequestException extends RuntimeException {
    private final String command;
    private final String data;

    public UnhandledRequestException(String command, String data) {
        super("Unhandled request: " + command + " " + data);
        this.command = command;
        this.data = data;
    }

    public UnhandledRequestException(String command, String data, Throwable t) {
        super("Unhandled request: " + command + " " + data, t);
        this.command = command;
        this.data = data;
    }
}
