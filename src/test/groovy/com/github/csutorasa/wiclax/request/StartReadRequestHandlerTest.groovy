package com.github.csutorasa.wiclax.request


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.response.ReadOkResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class StartReadRequestHandlerTest extends Specification {

    StartReadHandler handler = Mock()
    ResponseSender responseSender = Mock()
    StartReadRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new StartReadRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS, handler, responseSender)
        1 * handler.run()
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports("STARTREAD", "")
        requestHandler.handle("")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new StartReadRequestHandler(WiclaxProtocolOptions.builder().startReadCommand(customCommand).build(), handler, responseSender)
        1 * handler.run()
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle("")
    }
}
