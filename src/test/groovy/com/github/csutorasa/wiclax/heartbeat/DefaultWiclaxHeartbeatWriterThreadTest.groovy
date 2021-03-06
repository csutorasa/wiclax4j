package com.github.csutorasa.wiclax.heartbeat

import com.github.csutorasa.wiclax.exception.ErrorHandler
import com.github.csutorasa.wiclax.message.HeartBeatMessage
import com.github.csutorasa.wiclax.message.MessageSender
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class DefaultWiclaxHeartbeatWriterThreadTest extends Specification {

    MessageSender messageSender = Mock()

    def "sending works"() {
        given:
        def writer = new DefaultWiclaxHeartbeatWriterThread()
        when:
        writer.send(messageSender)
        then:
        1 * messageSender.send(_) >> { WiclaxMessage message ->
            assert message instanceof HeartBeatMessage
        }
    }

    def "sending message exception handling works"() {
        given:
        ErrorHandler<Exception> handler = Mock()
        Exception exception = new IOException("test error")
        def writer = new DefaultWiclaxHeartbeatWriterThread(5000, handler, null)
        when:
        writer.send(messageSender)
        then:
        1 * messageSender.send(_) >> { WiclaxMessage message ->
            assert message instanceof HeartBeatMessage
            throw exception
        }
        1 * handler.handle(exception)
    }
}
