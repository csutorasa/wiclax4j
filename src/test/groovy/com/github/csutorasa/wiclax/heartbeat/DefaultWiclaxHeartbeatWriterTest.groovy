package com.github.csutorasa.wiclax.heartbeat

import com.github.csutorasa.wiclax.exception.ErrorHandler
import com.github.csutorasa.wiclax.heartbeat.DefaultWiclaxHeartbeatWriter
import com.github.csutorasa.wiclax.message.HeartBeatMessage
import com.github.csutorasa.wiclax.message.MessageSender
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class DefaultWiclaxHeartbeatWriterTest extends Specification {

    MessageSender messageSender = Mock()

    def "sending works"() {
        given:
        def writer = new DefaultWiclaxHeartbeatWriter()
        when:
        writer.send(messageSender)
        then:
        1 * messageSender.send(_) >> { WiclaxMessage message ->
            assert message instanceof HeartBeatMessage
        }
    }

    def "thread message exception handling works"() {
        given:
        ErrorHandler<Throwable> handler = Mock()
        Exception exception = new RuntimeException("test error")
        def writer = new DefaultWiclaxHeartbeatWriter(5000, ErrorHandler.rethrow(), handler)
        when:
        writer.send(messageSender)
        then:
        1 * messageSender.send(_) >> { WiclaxMessage message ->
            assert message instanceof HeartBeatMessage
            throw exception
        }
        1 * handler.handle(exception)
    }

    def "sending message exception handling works"() {
        given:
        ErrorHandler<Exception> handler = Mock()
        Exception exception = new IOException("test error")
        def writer = new DefaultWiclaxHeartbeatWriter(5000, handler, null)
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
