package com.github.csutorasa.wiclax.heartbeat

import com.github.csutorasa.wiclax.message.HeartBeatMessage
import com.github.csutorasa.wiclax.message.MessageSender
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class ManualWiclaxHeartbeatWriterTest extends Specification {

    MessageSender messageSender = Mock()

    def "sending works"() {
        given:
        def writer = new ManualWiclaxHeartbeatWriter()
        when:
        writer.startWrite(null, messageSender)
        writer.send()
        then:
        1 * messageSender.send(_) >> { WiclaxMessage message ->
            assert message instanceof HeartBeatMessage
        }
    }

    def "throws error is sending message fails"() {
        given:
        Exception exception = new IOException("test error")
        def writer = new ManualWiclaxHeartbeatWriter()
        when:
        writer.startWrite(null, messageSender)
        writer.send()
        then:
        1 * messageSender.send(_) >> { WiclaxMessage message ->
            assert message instanceof HeartBeatMessage
            throw exception
        }
        thrown(IOException)
    }
}
