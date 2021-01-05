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

    /**
     * Runs a task, that can throw any exceptions, which will be handled by the error handler.
     *
     * @param supplier     supplier logic
     * @param errorHandler error handling logic
     * @param defaultValue default value to return if the error handling logic does not (re)throw an exception
     * @param <R> result type
     * @return result from the supplier
     */
    static <R> R runWithHandler(ThrowingSupplier<R> supplier, ErrorHandler<Throwable> errorHandler, R defaultValue) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            errorHandler.handle(t);
            return defaultValue;
        }
    }

    /**
     * Interface that can get a value and throw any exceptions.
     *
     * @param <T> result type
     */
    @FunctionalInterface
    interface ThrowingSupplier<T> {
        /**
         * Gets a value and can throw any exceptions.
         *
         * @return value
         * @throws Throwable any exception
         */
        T get() throws Throwable;
    }
}
