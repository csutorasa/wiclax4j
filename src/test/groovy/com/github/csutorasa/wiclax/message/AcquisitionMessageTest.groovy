package com.github.csutorasa.wiclax.message

import com.github.csutorasa.wiclax.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.model.Acquisition
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class AcquisitionMessageTest extends Specification {

    def "default to data works"() {
        setup:
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        def acquisition = new Acquisition('chipId', detectionTime, 'deviceId', 1, 3, true)
        def message = new AcquisitionMessage(acquisition, WiclaxProtocolOptions.defaults())
        expect:
        "chipId;03-12-2007 10:15:30.200;deviceId;1;3;1" == message.toData()
    }

    def "default to data works with null values"() {
        setup:
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        def acquisition = new Acquisition('chipId', detectionTime, 'deviceId', null, null, false)
        def message = new AcquisitionMessage(acquisition, WiclaxProtocolOptions.defaults())
        expect:
        "chipId;03-12-2007 10:15:30.200;deviceId;;;0" == message.toData()
    }

    def "custom separator works"() {
        setup:
        String separator = ","
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        def acquisition = new Acquisition('chipId', detectionTime, 'deviceId', 1, 3, true)
        def message = new AcquisitionMessage(acquisition, WiclaxProtocolOptions.builder().passingDataSeparator(separator).build())
        expect:
        "chipId,03-12-2007 10:15:30.200,deviceId,1,3,1" == message.toData()
    }

    def "custom data format works"() {
        setup:
        String separator = ","
        String mask = "C,YYYYMMDDhhmmssccc,@"
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        def acquisition = new Acquisition('chipId', detectionTime, 'deviceId', 1, 3, true)
        def message = new AcquisitionMessage(acquisition, WiclaxProtocolOptions.builder().passingDataSeparator(separator).passingDataMask(mask).build())
        expect:
        "chipId,20071203101530200,deviceId" == message.toData()
    }

    def "custom data format without separator works"() {
        setup:
        String separator = ""
        String mask = "CCCCCCCCCCC YYYYMMDDhhmmssccc @@"
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        def acquisition = new Acquisition('12345678901', detectionTime, '21', 1, 3, true)
        def message = new AcquisitionMessage(acquisition, WiclaxProtocolOptions.builder().passingDataSeparator(separator).passingDataMask(mask).build())
        expect:
        "12345678901 20071203101530200 21" == message.toData()
    }
}
