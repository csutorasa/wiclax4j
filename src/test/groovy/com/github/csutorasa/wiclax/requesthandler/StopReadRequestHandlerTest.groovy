package com.github.csutorasa.wiclax.requesthandler


import com.github.csutorasa.wiclax.request.StopReadRequest
import com.github.csutorasa.wiclax.response.ReadOkResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class StopReadRequestHandlerTest extends Specification {

    StopReadHandler handler = Mock()
    def requestHandler = new StopReadRequestHandler(handler)

    def "it works"() {
        given:
        def request = new StopReadRequest()
        when:
        boolean supports = requestHandler.supports(request)
        then:
        supports
        when:
        WiclaxResponse response = requestHandler.handle(request)
        then:
        1 * handler.run()
        response instanceof ReadOkResponse
    }
}
