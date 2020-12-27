package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.SetClockRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class SetClockRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String command = "CLOCK"
        String data = "03-12-2007 10:15:30"
        requestParser = new SetClockRequestParser(WiclaxProtocolOptions.defaults())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof SetClockRequest
    }

    def "custom works"() {
        given:
        String command = "TIME"
        String data = "= 2007/12/3 10:15:30"
        requestParser = new SetClockRequestParser(WiclaxProtocolOptions.builder().setClockCommand("TIME = YYYY/M/D hh:mm:ss").build())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof SetClockRequest
        (request as SetClockRequest).time
    }
}
