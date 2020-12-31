package com.github.csutorasa.wiclax.response

import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId

class ClockResponseTest extends Specification {

    ClockResponse response

    def "default formatter works"() {
        setup:
        response = new ClockResponse(LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant(), null)
        expect:
        "CLOCK 03-12-2007 10:15:30" == response.toData()
    }

    @Ignore
    def "custom formatter works"() {
        setup:
        response = new ClockResponse(LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant(), 'TIME = YYYY/M/D hh:mm:ss')
        expect:
        "TIME = 2007/12/03 10:15:30" == response.toData()
    }
}
