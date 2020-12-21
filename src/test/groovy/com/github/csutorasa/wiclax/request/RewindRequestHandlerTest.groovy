package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.WiclaxProtocolOptions
import spock.lang.Specification

class RewindRequestHandlerTest extends Specification {

    RewindHandler handler = Mock()
    WiclaxClientConnection connection = Mock()
    RewindRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new RewindRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS, handler)
        String data = "03-12-2007 10:15:30 03-12-2007 10:15:30"
        1 * handler.accept(_, _)
        expect:
        requestHandler.supports("REWIND", data)
        requestHandler.handle(connection, data)
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new RewindRequestHandler(WiclaxProtocolOptions.builder().rewindCommand(customCommand).build(), handler)
        String data = "03-12-2007 10:15:30 03-12-2007 10:15:30"
        1 * handler.accept(_, _)
        expect:
        requestHandler.supports(customCommand, data)
        requestHandler.handle(connection, data)
    }
}
