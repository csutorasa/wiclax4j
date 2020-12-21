package com.github.csutorasa.wiclax.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Acquisition data.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Acquisition {
    /**
     * Chip or bib identifier
     */
    private String chipId;
    /**
     * Detection date and time
     */
    private Instant detectionTime;
    /**
     * Optional device identifier
     */
    private String deviceId;
    /**
     * Optional lap number
     */
    private Integer lap;
    /**
     * Optional transponder battery level
     */
    private Integer batteryLevel;
    /**
     * True if it is a response to rewind request
     */
    private boolean rewind;
}