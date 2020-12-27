package com.github.csutorasa.wiclax.requesthandler;

import java.time.Instant;
import java.util.function.BiConsumer;

/**
 * Rewind request handler interface.
 */
@FunctionalInterface
public interface RewindHandler extends BiConsumer<Instant, Instant> {
    @Override
    void accept(Instant from, Instant to);
}
