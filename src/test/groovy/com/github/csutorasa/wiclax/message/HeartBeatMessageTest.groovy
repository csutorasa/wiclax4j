package com.github.csutorasa.wiclax.message

import spock.lang.Specification

class HeartBeatMessageTest extends Specification {

    def message = new HeartBeatMessage()

    def "to data works"() {
        expect:
        "*" == message.toData()
    }
}
