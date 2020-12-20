package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.WiclaxDateFormatters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AcquisitionMessage extends WiclaxMessage {

    private String chipId;
    private Instant detectionTime;
    private String deviceId;
    private Integer lap;
    private Integer batteryLevel;
    private boolean rewind;

    @Override
    public String toData() {
        return String.join(";",
                chipId,
                WiclaxDateFormatters.DATE_TIME_WITH_MILLIS_FORMATTER.format(detectionTime),
                deviceId,
                lap == null ? "" : Integer.toString(lap),
                batteryLevel == null ? "" : Integer.toString(batteryLevel),
                rewind ? "1" : "0");
    }
}
