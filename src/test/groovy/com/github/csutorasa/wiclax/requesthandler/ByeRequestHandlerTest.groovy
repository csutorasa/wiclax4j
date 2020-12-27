package com.github.csutorasa.wiclax.requesthandler

import com.github.csutorasa.wiclax.request.ByeRequest
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class ByeRequestHandlerTest extends Specification {

    def requestHandler = new ByeRequestHandler()

    def "it works"() {
        given:
        def request = new ByeRequest()
        when:
        boolean supports = requestHandler.supports(request)
        then:
        supports
        when:
        WiclaxResponse response = requestHandler.handle(request)
        then:
        !response
    }
}
