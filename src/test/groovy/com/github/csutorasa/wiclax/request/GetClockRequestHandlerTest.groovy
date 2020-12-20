package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.message.ClockResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

import java.time.Instant

class GetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    WiclaxClientConnection connection = Mock()
    GetClockRequestHandler requestHandler = new GetClockRequestHandler()

    def "it works"() {
        setup:
        1 * connection.getClock() >> clock
        1 * clock.getDateTime() >> Instant.now()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ClockResponse
        }
        expect:
        requestHandler.supports("CLOCK", "")
        requestHandler.handle(connection, "")
    }
}
