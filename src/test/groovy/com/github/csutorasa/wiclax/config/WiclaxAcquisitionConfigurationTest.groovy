package com.github.csutorasa.wiclax.config

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class WiclaxAcquisitionConfigurationTest extends Specification {

    def "simple to xml works"() {
        given:
        def options = WiclaxProtocolOptions.builder().build()
        def configuration = new WiclaxAcquisitionConfiguration(options, "Name", null)
        def byteStream = new ByteArrayOutputStream()

        when:
        configuration.writeTo(new OutputStreamWriter(byteStream))

        then:
        new String(byteStream.toByteArray(), StandardCharsets.UTF_8) == '''<?xml version="1.0" encoding="UTF-8"?><Acquisition name="Name"/>'''
    }
}
