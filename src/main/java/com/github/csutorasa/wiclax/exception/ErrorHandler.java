package com.github.csutorasa.wiclax.exception;

/**
 * Generic error handler.
 *
 * @param <T> error type
 */
@FunctionalInterface
public interface ErrorHandler<T extends Throwable> {
    /**
     * Handles the error. It can rethrow the error.
     *
     * @param error error
     */
    void handle(T error);

    /**
     * Creates an error handler that rethrows all exceptions.
     *
     * @param <T> error type
     * @return error handler
     */
    static <T extends Throwable> ErrorHandler<T> rethrow() {
        return (t) -> {
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new RuntimeException(t);
        };
    }
}
