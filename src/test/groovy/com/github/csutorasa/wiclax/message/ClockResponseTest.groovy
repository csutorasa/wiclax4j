package com.github.csutorasa.wiclax.message

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId

class ClockResponseTest extends Specification {

    def message = new ClockResponse(LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant())

    def "to data works"() {
        expect:
        "CLOCK 03-12-2007 10:15:30" == message.toData()
    }
}
