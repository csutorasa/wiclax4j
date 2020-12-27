package com.github.csutorasa.wiclax.request;

/**
 * This can store the implementation to read a request command.
 */
@FunctionalInterface
public interface RequestReader {
    /**
     * Reads the stream until the delimiter.
     *
     * @return read data or null if there are no more requests in the stream
     */
    String readRequest();
}
