package com.github.csutorasa.wiclax.requesthandler

import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.request.SetClockRequest
import com.github.csutorasa.wiclax.response.ClockOkResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

import java.time.Instant

class SetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    def requestHandler = new SetClockRequestHandler(clock)

    def "it works"() {
        given:
        Instant time = Instant.now()
        def request = new SetClockRequest(time)
        when:
        boolean supports = requestHandler.supports(request)
        then:
        supports
        when:
        WiclaxResponse response = requestHandler.handle(request)
        then:
        1 * clock.setDateTime(time)
        response instanceof ClockOkResponse
    }
}
