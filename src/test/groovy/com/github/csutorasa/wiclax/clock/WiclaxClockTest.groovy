package com.github.csutorasa.wiclax.clock

import spock.lang.Specification

import java.time.Instant
import java.util.function.Supplier

class WiclaxClockTest extends Specification {

    Supplier<Instant> nowGetter = Mock()
    WiclaxClock clock = new WiclaxClock(nowGetter)

    def "clock test"() {
        given:
        def now = Instant.now()
        1 * nowGetter.get() >> now
        when:
        Instant value = clock.getDateTime()
        then: "clock returns now without settings"
        value == now
        when:
        2 * nowGetter.get() >> now
        clock.setDateTime(now.plusSeconds(-10))
        then: "clock returns correct value with earlier date"
        clock.getDateTime() == now.plusSeconds(-10)
        when:
        2 * nowGetter.get() >> now
        clock.setDateTime(now.plusSeconds(10))
        then: "clock returns correct value with later date"
        clock.getDateTime() == now.plusSeconds(10)
        when:
        1 * nowGetter.get() >> now.plusSeconds(10)
        then: "clock returns correct value with new now value"
        clock.getDateTime() == now.plusSeconds(20)
    }
}
