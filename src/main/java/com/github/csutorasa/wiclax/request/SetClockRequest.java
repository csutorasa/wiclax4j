package com.github.csutorasa.wiclax.request;

import lombok.Value;

import java.time.Instant;

/**
 * Request to set the clock time.
 */
@Value
public class SetClockRequest implements WiclaxRequest {
    Instant time;
}
