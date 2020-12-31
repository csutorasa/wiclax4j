package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.StartReadRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class StartReadRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String requestLine = "STARTREAD"
        requestParser = new StartReadRequestParser(WiclaxProtocolOptions.defaults())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof StartReadRequest
    }

    def "custom works"() {
        given:
        String requestLine = "TEST"
        requestParser = new StartReadRequestParser(WiclaxProtocolOptions.builder().startReadCommand(requestLine).build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof StartReadRequest
    }
}
