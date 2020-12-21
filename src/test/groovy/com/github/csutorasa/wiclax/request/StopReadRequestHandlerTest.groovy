package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.message.ReadOkResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class StopReadRequestHandlerTest extends Specification {

    StopReadHandler handler = Mock()
    WiclaxClientConnection connection = Mock()
    StopReadRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new StopReadRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS, handler)
        1 * handler.run()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports("STOPREAD", "")
        requestHandler.handle(connection, "")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new StopReadRequestHandler(WiclaxProtocolOptions.builder().stopReadCommand(customCommand).build(), handler)
        1 * handler.run()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle(connection, "")
    }
}
