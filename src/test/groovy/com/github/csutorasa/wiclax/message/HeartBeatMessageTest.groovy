package com.github.csutorasa.wiclax.message

import com.github.csutorasa.wiclax.WiclaxProtocolOptions
import spock.lang.Specification

class HeartBeatMessageTest extends Specification {

    def message = new HeartBeatMessage()

    def "default to data works"() {
        setup:
        message = new HeartBeatMessage(WiclaxProtocolOptions.defaults())
        expect:
        "*" == message.toData()
    }

    def "custom to data works"() {
        setup:
        String customCommand = "-"
        message = new HeartBeatMessage(WiclaxProtocolOptions.builder().heartbeatValue(customCommand).build())
        expect:
        customCommand == message.toData()
    }
}
