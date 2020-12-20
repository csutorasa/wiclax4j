package com.github.csutorasa.wiclax.message

import spock.lang.Specification

class ClockOkResponseTest extends Specification {

    def message = new ClockOkResponse()

    def "to data works"() {
        expect:
        "CLOCKOK" == message.toData()
    }
}
