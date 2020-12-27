package com.github.csutorasa.wiclax.requesthandler


import com.github.csutorasa.wiclax.request.InitializationRequest
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class InitializationRequestHandlerTest extends Specification {

    def requestHandler = new InitializationRequestHandler()

    def "it works"() {
        given:
        def request = new InitializationRequest()
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
