package com.github.csutorasa.wiclax.response

import com.github.csutorasa.wiclax.response.ReadOkResponse
import spock.lang.Specification

class ReadOkResponseTest extends Specification {

    def message = new ReadOkResponse()

    def "to data works"() {
        expect:
        "READOK" == message.toData()
    }
}