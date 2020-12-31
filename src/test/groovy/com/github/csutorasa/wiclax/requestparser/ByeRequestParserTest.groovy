package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.request.ByeRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class ByeRequestParserTest extends Specification {

    def requestParser = new ByeRequestParser()

    def "it works"() {
        given:
        String requestLine = "BYE"
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof ByeRequest
    }
}
