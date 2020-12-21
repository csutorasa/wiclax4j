package com.github.csutorasa.wiclax.message;

import com.github.csutorasa.wiclax.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.formatter.WiclaxAcuqisitionFormatter;
import com.github.csutorasa.wiclax.model.Acquisition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

/**
 * Message which contains an acquisition.
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AcquisitionMessage extends WiclaxMessage {

    private static final String DEFAULT_PASSING_DATA_SEPARATOR = ";";

    private final Acquisition acquisition;
    private final WiclaxProtocolOptions protocolOptions;

    @Override
    public String toData() {
        Optional<String> mask = protocolOptions.get(WiclaxProtocolOptions::getPassingDataMask);
        Optional<String> separator = protocolOptions.get(WiclaxProtocolOptions::getPassingDataSeparator);
        WiclaxAcuqisitionFormatter formatter;
        if (separator.isPresent()) {
            if (mask.isPresent()) {
                formatter = WiclaxAcuqisitionFormatter.ofPatternAndSeparator(mask.get(), separator.get());
            } else {
                formatter = WiclaxAcuqisitionFormatter.ofSeparator(separator.get());
            }
        } else {
            formatter = WiclaxAcuqisitionFormatter.DEFAULT_FORMATTER;
        }
        return formatter.format(acquisition);
    }
}
