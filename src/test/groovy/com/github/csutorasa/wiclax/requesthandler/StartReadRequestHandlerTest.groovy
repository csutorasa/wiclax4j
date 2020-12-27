package com.github.csutorasa.wiclax.requesthandler


import com.github.csutorasa.wiclax.request.StartReadRequest
import com.github.csutorasa.wiclax.response.ReadOkResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class StartReadRequestHandlerTest extends Specification {

    StartReadHandler handler = Mock()
    def requestHandler = new StartReadRequestHandler(handler)

    def "it works"() {
        given:
        def request = new StartReadRequest()
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
