package com.github.csutorasa.wiclax.request

import com.github.csutorasa.wiclax.clock.WiclaxClock
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.response.ClockOkResponse
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

class SetClockRequestHandlerTest extends Specification {

    WiclaxClock clock = Mock()
    ResponseSender responseSender = Mock()
    SetClockRequestHandler requestHandler

    def "default works"() {
        setup:
        String data = "03-12-2007 10:15:30"
        requestHandler = new SetClockRequestHandler(WiclaxProtocolOptions.defaults(), clock, responseSender)
        1 * clock.setDateTime(_)
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ClockOkResponse
        }
        expect:
        requestHandler.supports("CLOCK", data)
        requestHandler.handle(data)
    }

    def "custom works"() {
        setup:
        String customCommand = "TIME = YYYY/M/D hh:mm:ss"
        requestHandler = new SetClockRequestHandler(WiclaxProtocolOptions.builder().setClockCommand(customCommand).build(),
                clock, responseSender)
        String data = "= 2007/12/3 10:15:30"
        1 * clock.setDateTime(_)
        1 * responseSender.send(_) >> { WiclaxResponse response ->
            assert response instanceof ClockOkResponse
        }
        expect:
        requestHandler.supports("TIME", data)
        requestHandler.handle(data)
    }
}
