package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.InitializationRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class InitializationRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String command = "HELLO"
        String data = ""
        requestParser = new InitializationRequestParser(WiclaxProtocolOptions.defaults())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof InitializationRequest
    }

    def "custom works"() {
        given:
        String command = "TEST"
        String data = ""
        requestParser = new InitializationRequestParser(WiclaxProtocolOptions.builder().commandsForInitialization(command).build())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof InitializationRequest
    }
}
