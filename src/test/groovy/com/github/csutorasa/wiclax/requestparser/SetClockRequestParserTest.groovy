package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.SetClockRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class SetClockRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String requestLine = "CLOCK 03-12-2007 10:15:30"
        requestParser = new SetClockRequestParser(WiclaxProtocolOptions.defaults())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof SetClockRequest
    }

    def "custom works"() {
        given:
        String requestLine = "TIMEYMD = 2007/12/3 10:15:30"
        requestParser = new SetClockRequestParser(WiclaxProtocolOptions.builder().setClockCommand("'TIMEYMD' '=' yyyy'/'M'/'d HH':'mm':'ss").build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof SetClockRequest
        (request as SetClockRequest).time
    }

    def "custom fails without error"() {
        given:
        String requestLine = "TEST"
        requestParser = new SetClockRequestParser(WiclaxProtocolOptions.builder().setClockCommand("'TIME' '=' yyyy'/'M'/'d HH':'mm':'ss").build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        !request
    }
}
