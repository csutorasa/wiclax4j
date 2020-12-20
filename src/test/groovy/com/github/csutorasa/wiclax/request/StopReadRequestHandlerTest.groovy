package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.message.ReadOkResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class StopReadRequestHandlerTest extends Specification {

    StopReadHandler handler = Mock()
    WiclaxClientConnection connection = Mock()
    StopReadRequestHandler requestHandler = new StopReadRequestHandler(handler)

    def "it works"() {
        setup:
        1 * handler.run()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ReadOkResponse
        }
        expect:
        requestHandler.supports("STOPREAD", "")
        requestHandler.handle(connection, "")
    }
}
