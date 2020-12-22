package com.github.csutorasa.wiclax.message

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId

class RaceStartMessageTest extends Specification {

    def message = new RaceStartMessage(LocalDateTime.parse("2007-12-03T10:15:30.20").atZone(ZoneId.systemDefault()).toInstant())

    def "to data works"() {
        expect:
        "RACESTART 10:15:30.200" == message.toData(WiclaxProtocolOptions.defaults())
    }
}
