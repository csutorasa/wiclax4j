package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import spock.lang.Specification

class ByeRequestHandlerTest extends Specification {

    ByeRequestHandler requestHandler = new ByeRequestHandler()

    def "it works"() {
        expect:
        requestHandler.supports("BYE", "")
        requestHandler.handle("")
    }
}
