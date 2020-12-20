package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import spock.lang.Specification

class RewindRequestHandlerTest extends Specification {

    RewindHandler handler = Mock()
    WiclaxClientConnection connection = Mock()
    RewindRequestHandler requestHandler = new RewindRequestHandler(handler)

    def "it works"() {
        setup:
        String data = "03-12-2007 10:15:30 03-12-2007 10:15:30"
        1 * handler.accept(_, _)
        expect:
        requestHandler.supports("REWIND", data)
        requestHandler.handle(connection, data)
    }
}
