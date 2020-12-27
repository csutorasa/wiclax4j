package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.RewindRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Specification

class RewindRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String command = "REWIND"
        String data = "03-12-2007 10:15:30 03-12-2007 10:15:30"
        requestParser = new RewindRequestParser(WiclaxProtocolOptions.defaults())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof RewindRequest
        (request as RewindRequest).from
        (request as RewindRequest).to
    }

    def "custom works"() {
        given:
        String command = "TEST"
        String data = "03-12-2007 10:15:30 03-12-2007 10:15:30"
        requestParser = new RewindRequestParser(WiclaxProtocolOptions.builder().rewindCommand(command).build())
        when:
        boolean supports = requestParser.supports(command, data)
        then:
        supports
        when:
        WiclaxRequest request = requestParser.parse(data)
        then:
        request instanceof RewindRequest
        (request as RewindRequest).from
        (request as RewindRequest).to
    }
}
