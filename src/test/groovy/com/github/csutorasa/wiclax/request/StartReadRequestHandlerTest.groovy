package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.message.ReadOkResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class StartReadRequestHandlerTest extends Specification {

    StartReadHandler handler = Mock()
    WiclaxClientConnection connection = Mock()
    StartReadRequestHandler requestHandler = new StartReadRequestHandler(handler)

    def "it works"() {
        setup:
        1 * handler.run()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports("STARTREAD", "")
        requestHandler.handle(connection, "")
    }
}
