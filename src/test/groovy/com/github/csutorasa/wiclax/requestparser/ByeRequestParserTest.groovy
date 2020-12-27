package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.request.ByeRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class ByeRequestParserTest extends Specification {

    def requestParser = new ByeRequestParser()

    def "it works"() {
        given:
        String command = "BYE"
        String data = ""
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof ByeRequest
    }
}
