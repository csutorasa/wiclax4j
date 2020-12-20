package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import spock.lang.Specification

class ByeRequestHandlerTest extends Specification {

    WiclaxClientConnection connection = Mock()
    ByeRequestHandler requestHandler = new ByeRequestHandler()

    def "it works"() {
        expect:
        requestHandler.supports("BYE", "")
        requestHandler.handle(connection, "")
    }
}
