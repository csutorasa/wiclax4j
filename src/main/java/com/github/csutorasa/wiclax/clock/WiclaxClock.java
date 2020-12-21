package com.github.csutorasa.wiclax.clock;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * Custom clock for synchronizing with Wiclax time.
 */
public class WiclaxClock {

    private final Supplier<Instant> nowGetter;
    /**
     * Delay to use when getting the time.
     */
    private Duration duration = Duration.ZERO;

    /**
     * Creates a new clock with the system time.
     */
    public WiclaxClock() {
        nowGetter = Instant::now;
    }

    /**
     * Creates a new clock with a custom getter.
     *
     * @param nowGetter supplier of the current time
     */
    public WiclaxClock(Supplier<Instant> nowGetter) {
        this.nowGetter = nowGetter;
    }

    /**
     * Gets the current time, based on the set delay.
     *
     * @return current time
     */
    public Instant getDateTime() {
        return nowGetter.get().plus(duration);
    }

    /**
     * Sets the current time and the delay.
     * This delay is used when getting the current time.
     *
     * @param dateTime current time
     */
    public void setDateTime(Instant dateTime) {
        duration = Duration.between(nowGetter.get(), dateTime);
    }
}
