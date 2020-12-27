package com.github.csutorasa.wiclax.requesthandler


import com.github.csutorasa.wiclax.request.RewindRequest
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

import java.time.Instant

class RewindRequestHandlerTest extends Specification {

    RewindHandler handler = Mock()
    def requestHandler = new RewindRequestHandler(handler)

    def "it works"() {
        given:
        Instant time = Instant.now()
        def request = new RewindRequest(time, time)
        when:
        boolean supports = requestHandler.supports(request)
        then:
        supports
        when:
        WiclaxResponse response = requestHandler.handle(request)
        then:
        1 * handler.accept(time, time)
        !response
    }
}
