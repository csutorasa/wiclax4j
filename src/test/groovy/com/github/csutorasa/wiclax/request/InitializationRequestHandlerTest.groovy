package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import spock.lang.Specification

class InitializationRequestHandlerTest extends Specification {

    WiclaxClientConnection connection = Mock()
    InitializationRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new InitializationRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS)
        expect:
        requestHandler.supports("HELLO", "")
        requestHandler.handle(connection, "")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new InitializationRequestHandler(WiclaxProtocolOptions.builder().commandsForInitialization(customCommand).build())
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle(connection, "")
    }
}
