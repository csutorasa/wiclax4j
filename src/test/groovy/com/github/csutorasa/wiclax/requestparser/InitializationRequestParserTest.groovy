package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.InitializationRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class InitializationRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String requestLine = "HELLO"
        requestParser = new InitializationRequestParser(WiclaxProtocolOptions.defaults())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof InitializationRequest
    }

    def "custom works"() {
        given:
        String requestLine = "TEST"
        requestParser = new InitializationRequestParser(WiclaxProtocolOptions.builder().commandsForInitialization(requestLine).build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof InitializationRequest
    }
}
