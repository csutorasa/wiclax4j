package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.response.ReadOkResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class StopReadRequestHandlerTest extends Specification {

    StopReadHandler handler = Mock()
    ResponseSender responseSender = Mock()
    StopReadRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new StopReadRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS, handler, responseSender)
        1 * handler.run()
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports("STOPREAD", "")
        requestHandler.handle("")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new StopReadRequestHandler(WiclaxProtocolOptions.builder().stopReadCommand(customCommand).build(), handler, responseSender)
        1 * handler.run()
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle("")
    }
}
