package com.github.csutorasa.wiclax.request


import spock.lang.Specification

class ByeRequestHandlerTest extends Specification {

    ByeRequestHandler requestHandler = new ByeRequestHandler()

    def "it works"() {
        expect:
        requestHandler.supports("BYE", "")
        requestHandler.handle("")
    }
}
