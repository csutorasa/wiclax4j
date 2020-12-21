package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.message.ReadOkResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class StartReadRequestHandlerTest extends Specification {

    StartReadHandler handler = Mock()
    WiclaxClientConnection connection = Mock()
    StartReadRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new StartReadRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS, handler)
        1 * handler.run()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports("STARTREAD", "")
        requestHandler.handle(connection, "")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new StartReadRequestHandler(WiclaxProtocolOptions.builder().startReadCommand(customCommand).build(), handler)
        1 * handler.run()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle(connection, "")
    }
}
