package com.github.csutorasa.wiclax.requesthandler

import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.request.GetClockRequest
import com.github.csutorasa.wiclax.response.ClockResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

import java.time.Instant

class GetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    def requestHandler = new GetClockRequestHandler(clock)

    def "it works"() {
        given:
        def request = new GetClockRequest()
        1 * clock.getDateTime() >> Instant.now()
        when:
        boolean supports = requestHandler.supports(request)
        then:
        supports
        when:
        WiclaxResponse response = requestHandler.handle(request)
        then:
        response instanceof ClockResponse
        (response as ClockResponse).dateTime
    }
}
