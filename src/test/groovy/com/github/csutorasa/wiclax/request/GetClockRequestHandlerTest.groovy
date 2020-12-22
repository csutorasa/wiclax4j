package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.response.ClockResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

import java.time.Instant

class GetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    ResponseSender responseSender = Mock()
    GetClockRequestHandler requestHandler

    def "default works"() {
        setup:
        requestHandler = new GetClockRequestHandler(WiclaxProtocolOptions.defaults(), clock, responseSender)
        1 * clock.getDateTime() >> Instant.now()
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ClockResponse
        }
        expect:
        requestHandler.supports("CLOCK", "")
        requestHandler.handle("")
    }

    def "custom works"() {
        setup:
        String customCommand = "TEST"
        requestHandler = new GetClockRequestHandler(WiclaxProtocolOptions.builder().getClockCommand(customCommand).build(), clock, responseSender)
        1 * clock.getDateTime() >> Instant.now()
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ClockResponse
        }
        expect:
        requestHandler.supports(customCommand, "")
        requestHandler.handle("")
    }
}
