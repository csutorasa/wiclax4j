package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.message.ClockResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

import java.time.Instant

class GetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    WiclaxClientConnection connection = Mock()
    GetClockRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new GetClockRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS)
        1 * connection.getClock() >> clock
        1 * clock.getDateTime() >> Instant.now()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ClockResponse
        }
        expect:
        requestHandler.supports("CLOCK", "")
        requestHandler.handle(connection, "")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new GetClockRequestHandler(WiclaxProtocolOptions.builder().getClockCommand(customCommand).build())
        1 * connection.getClock() >> clock
        1 * clock.getDateTime() >> Instant.now()
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ClockResponse
        }
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle(connection, "")
    }
}
