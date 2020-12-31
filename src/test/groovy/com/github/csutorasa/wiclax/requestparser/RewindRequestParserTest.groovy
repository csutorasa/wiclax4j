package com.github.csutorasa.wiclax.requestparser


import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.request.RewindRequest
import com.github.csutorasa.wiclax.request.WiclaxRequest
import spock.lang.Ignore
import spock.lang.Specification

class RewindRequestParserTest extends Specification {

    def requestParser

    def "default works"() {
        given:
        String requestLine = "REWIND 03-12-2007 10:15:30 03-12-2007 10:15:30"
        requestParser = new RewindRequestParser(WiclaxProtocolOptions.defaults())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof RewindRequest
        (request as RewindRequest).from
        (request as RewindRequest).to
    }

    @Ignore
    def "custom works"() {
        given:
        String requestLine = "TESTYMD 2007/12/3 10:15:30 2007/12/3 10:15:30"
        requestParser = new RewindRequestParser(WiclaxProtocolOptions.builder().rewindCommand("'TESTYMD' YYYY'/'M'/'D hh':'mm':'ss YYYY'/'M'/'D hh':'mm':'ss").build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        request instanceof RewindRequest
        (request as RewindRequest).from
        (request as RewindRequest).to
    }

    def "custom fails without error"() {
        given:
        String requestLine = "TEST"
        requestParser = new RewindRequestParser(WiclaxProtocolOptions.builder().rewindCommand("'TESTYMD' YYYY'/'M'/'D hh':'mm':'ss YYYY'/'M'/'D hh':'mm':'ss").build())
        when:
        WiclaxRequest request = requestParser.parse(requestLine)
        then:
        !request
    }
}
