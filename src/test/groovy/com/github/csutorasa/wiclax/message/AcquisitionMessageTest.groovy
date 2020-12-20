package com.github.csutorasa.wiclax.message

import spock.lang.Specification

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class AcquisitionMessageTest extends Specification {

    def "to data works"() {
        setup:
        String chipId = 'chipId'
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        String deviceId = 'deviceId'
        Integer lap = 1
        Integer batteryLevel = 3
        boolean rewind = true
        def message = new AcquisitionMessage(chipId, detectionTime, deviceId, lap, batteryLevel, rewind)
        expect:
        "chipId;03-12-2007 10:15:30.200;deviceId;1;3;1" == message.toData()
    }

    def "to data works with null values"() {
        setup:
        String chipId = 'chipId'
        Instant detectionTime = LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant()
        String deviceId = 'deviceId'
        Integer lap = null
        Integer batteryLevel = null
        boolean rewind = false
        def message = new AcquisitionMessage(chipId, detectionTime, deviceId, lap, batteryLevel, rewind)
        expect:
        "chipId;03-12-2007 10:15:30.200;deviceId;;;0" == message.toData()
    }
}
