package com.github.csutorasa.wiclax.requestparser

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.StartReadRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class StartReadRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String command = "STARTREAD"
        String data = ""
        requestParser = new StartReadRequestParser(WiclaxProtocolOptions.defaults())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof StartReadRequest
    }

    def "custom works"() {
        given:
        String command = "TEST"
        String data = ""
        requestParser = new StartReadRequestParser(WiclaxProtocolOptions.builder().startReadCommand(command).build())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof StartReadRequest
    }
}
