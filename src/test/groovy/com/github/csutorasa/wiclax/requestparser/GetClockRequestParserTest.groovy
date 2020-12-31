package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.GetClockRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class GetClockRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String requestLine = "CLOCK"
        requestParser = new GetClockRequestParser(WiclaxProtocolOptions.defaults())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof GetClockRequest
    }

    def "custom works"() {
        given:
        String requestLine = "TEST"
        requestParser = new GetClockRequestParser(WiclaxProtocolOptions.builder().getClockCommand(requestLine).build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof GetClockRequest
    }
}
