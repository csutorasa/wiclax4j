package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.message.ClockOkResponse
import com.github.csutorasa.wiclax.message.WiclaxMessage
import spock.lang.Specification

class SetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    WiclaxClientConnection connection = Mock()
    SetClockRequestHandler requestHandler

    def "default works"() {
        setup:
        String data = "03-12-2007 10:15:30"
        requestHandler = new SetClockRequestHandler(WiclaxProtocolOptions.DEFAULT_OPTIONS)
        1 * connection.getClock() >> clock
        1 * clock.setDateTime(_)
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ClockOkResponse
        }
        expect:
        requestHandler.supports("CLOCK", data)
        requestHandler.handle(connection, data)
    }

    def "custom works"() {
        setup:
        String customCommand = "TIME = YYYY/M/D hh:mm:ss"
        requestHandler = new SetClockRequestHandler(WiclaxProtocolOptions.builder().setClockCommand(customCommand).build())
        String data = "= 2007/12/3 10:15:30"
        1 * connection.getClock() >> clock
        1 * clock.setDateTime(_)
        1 * connection.send(_) >> { WiclaxMessage message ->
            assert message instanceof ClockOkResponse
        }
        expect:
        requestHandler.supports("TIME", data)
        requestHandler.handle(connection, data)
    }
}
