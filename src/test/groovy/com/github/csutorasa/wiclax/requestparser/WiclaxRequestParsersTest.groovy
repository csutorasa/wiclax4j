package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.exception.UnparseableRequestException
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class WiclaxRequestParsersTest extends Specification {

    def "from all parsers works"() {
        given:
        def connection = Mock(WiclaxClientConnection)
        def protocolOptions = WiclaxProtocolOptions.defaults()
        connection.getProtocolOptions() >> protocolOptions

        when:
        WiclaxRequestParsers.fromAllParsers(connection)

        then:
        noExceptionThrown()
    }

    def "parse works"() {
        given:
        String line = "testline"
        def request = Mock(WiclaxRequest)
        def parser = Mock(WiclaxRequestParser)
        def parsers = new WiclaxRequestParsers([parser])

        when:
        def result = parsers.parse(line)

        then:
        result == request
        1 * parser.parse(line) >> request
    }

    def "parse throws exception if cannot parse the request"() {
        given:
        String line = "testline"
        def parser = Mock(WiclaxRequestParser)
        def parsers = new WiclaxRequestParsers([parser])

        when:
        parsers.parse(line)

        then:
        1 * parser.parse(line) >> null
        thrown(UnparseableRequestException)
    }
}
