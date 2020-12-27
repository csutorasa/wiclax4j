package com.github.csutorasa.wiclax.request;

import lombok.Value;

import java.time.Instant;

/**
 * Rewind request to resend all acquisitions between the from and to times.
 */
@Value
public class RewindRequest implements WiclaxRequest {
    Instant from;
    Instant to;
}
