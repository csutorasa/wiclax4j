package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.StopReadRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class StopReadRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String requestLine = "STOPREAD"
        requestParser = new StopReadRequestParser(WiclaxProtocolOptions.defaults())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof StopReadRequest
    }

    def "custom works"() {
        given:
        String requestLine = "TEST"
        requestParser = new StopReadRequestParser(WiclaxProtocolOptions.builder().stopReadCommand(requestLine).build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof StopReadRequest
    }
}
