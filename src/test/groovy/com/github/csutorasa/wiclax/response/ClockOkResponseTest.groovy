package com.github.csutorasa.wiclax.response

import com.github.csutorasa.wiclax.response.ClockOkResponse
import spock.lang.Specification

class ClockOkResponseTest extends Specification {

    def response = new ClockOkResponse()

    def "to data works"() {
        expect:
        "CLOCKOK" == response.toData()
    }
}
