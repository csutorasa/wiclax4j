package com.github.csutorasa.wiclax.formatter

import com.github.csutorasa.wiclax.model.Acquisition
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class WiclaxAcquisitionFormatterTest extends Specification {

    Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
    WiclaxAcuqisitionFormatter formatter
    Acquisition acquisition

    def "default works"() {
        setup:
        formatter = WiclaxAcuqisitionFormatter.DEFAULT_FORMATTER
        acquisition = new Acquisition('chipId', detectionTime, 'deviceId', 1, 3, true)
        expect:
        "chipId;03-12-2007 10:15:30.200;deviceId;1;3;1" == formatter.format(acquisition)
    }

    def "default works with null values"() {
        setup:
        formatter = WiclaxAcuqisitionFormatter.DEFAULT_FORMATTER
        acquisition = new Acquisition('chipId', detectionTime, null, null, null, false)
        expect:
        "chipId;03-12-2007 10:15:30.200;;;;0" == formatter.format(acquisition)
    }

    def "custom ofSeparator works"() {
        setup:
        formatter = WiclaxAcuqisitionFormatter.ofSeparator(",")
        acquisition = new Acquisition('chipId', detectionTime, 'deviceId', 1, 3, true)
        expect:
        "chipId,03-12-2007 10:15:30.200,deviceId,1,3,1" == formatter.format(acquisition)
    }

    def "custom ofPatternAndSeparator works"() {
        setup:
        formatter = WiclaxAcuqisitionFormatter.ofPatternAndSeparator("C,YYYYMMDDhhmmssccc,@", ",")
        acquisition = new Acquisition('chipId', detectionTime, 'deviceId', 1, 3, true)
        expect:
        "chipId,20071203101530200,deviceId" == formatter.format(acquisition)
    }

    def "custom ofPattern works without separator"() {
        setup:
        formatter = WiclaxAcuqisitionFormatter.ofPattern("CCCCCCCCCCC YYYYMMDDhhmmssccc @@")
        acquisition = new Acquisition('12345678901', detectionTime, '21', 1, 3, true)
        expect:
        "12345678901 20071203101530200 21" == formatter.format(acquisition)
    }

    def "custom ofPatternAndSeparator works without separator"() {
        setup:
        formatter = WiclaxAcuqisitionFormatter.ofPatternAndSeparator("CCCCCCCCCCC YYYYMMDDhhmmssccc @@", "")
        acquisition = new Acquisition('12345678901', detectionTime, '21', 1, 3, true)
        expect:
        "12345678901 20071203101530200 21" == formatter.format(acquisition)
    }
}
