package com.github.csutorasa.wiclax.response

import spock.lang.Specification

class ReadOkResponseTest extends Specification {

    def response = new ReadOkResponse()

    def "to data works"() {
        expect:
        "READOK" == response.toData()
    }
}
