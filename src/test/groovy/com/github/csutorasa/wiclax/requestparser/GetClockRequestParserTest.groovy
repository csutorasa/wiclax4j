package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.GetClockRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class GetClockRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String command = "CLOCK"
        String data = ""
        requestParser = new GetClockRequestParser(WiclaxProtocolOptions.defaults())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof GetClockRequest
    }

    def "custom works"() {
        given:
        String command = "TEST"
        String data = ""
        requestParser = new GetClockRequestParser(WiclaxProtocolOptions.builder().getClockCommand(command).build())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof GetClockRequest
    }
}
