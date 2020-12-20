package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import spock.lang.Specification

class HelloRequestHandlerTest extends Specification {

    WiclaxClientConnection connection = Mock()
    HelloRequestHandler requestHandler = new HelloRequestHandler()

    def "it works"() {
        expect:
        requestHandler.supports("HELLO", "")
        requestHandler.handle(connection, "")
    }
}
