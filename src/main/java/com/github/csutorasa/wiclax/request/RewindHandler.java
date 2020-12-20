package com.github.csutorasa.wiclax.request;

import java.time.Instant;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface RewindHandler extends BiConsumer<Instant, Instant> {
    @Override
    void accept(Instant from, Instant to);
}
