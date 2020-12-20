package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.WiclaxDateFormatters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Message which contains an acquisition.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AcquisitionMessage extends WiclaxMessage {

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

    @Override
    public String toData() {
        return String.join(";",
                chipId,
                WiclaxDateFormatters.DATE_TIME_WITH_MILLIS_FORMATTER.format(detectionTime),
                orEmpty(deviceId),
                orEmpty(lap),
                orEmpty(batteryLevel),
                rewind ? "1" : "0");
    }

    private String orEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
