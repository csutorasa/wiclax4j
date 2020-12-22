package com.github.csutorasa.wiclax.message

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import spock.lang.Specification

class HeartBeatMessageTest extends Specification {

    def message = new HeartBeatMessage()

    def "default to data works"() {
        setup:
        message = new HeartBeatMessage()
        expect:
        "*" == message.toData(WiclaxProtocolOptions.defaults())
    }

    def "custom to data works"() {
        setup:
        String customCommand = "-"
        message = new HeartBeatMessage()
        expect:
        customCommand == message.toData(WiclaxProtocolOptions.builder().heartbeatValue(customCommand).build())
    }
}
