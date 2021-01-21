package com.github.csutorasa.wiclax.requesthandler

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.exception.UnhandledRequestException
import com.github.csutorasa.wiclax.request.WiclaxRequest
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class WiclaxRequestHandlersTest extends Specification {

    def "from all handlers works"() {
        given:
        def connection = Mock(WiclaxClientConnection)
        def protocolOptions = WiclaxProtocolOptions.defaults()
        connection.getProtocolOptions() >> protocolOptions

        when:
        WiclaxRequestHandlers.fromAllHandlers(connection, { }, { }, { })

        then:
        noExceptionThrown()
    }

    def "handle works"() {
        given:
        def request = Mock(WiclaxRequest)
        def response = Mock(WiclaxResponse)
        def handler = Mock(WiclaxRequestHandler)
        def handlers = new WiclaxRequestHandlers([handler])

        when:
        def result = handlers.handle(request)

        then:
        result == response
        1 * handler.supports(request) >> true
        1 * handler.handle(request) >> response
    }

    def "handle throws exception if cannot handle the request"() {
        given:
        def request = Mock(WiclaxRequest)
        def handler = Mock(WiclaxRequestHandler)
        def handlers = new WiclaxRequestHandlers([handler])
        request.toString() >> ""

        when:
        handlers.handle(request)

        then:
        1 * handler.supports(request) >> false
        thrown(UnhandledRequestException)
    }
}
