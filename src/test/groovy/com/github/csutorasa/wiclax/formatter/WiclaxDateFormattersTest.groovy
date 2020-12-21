package com.github.csutorasa.wiclax.formatter

import spock.lang.Specification

import java.time.LocalDateTime

class WiclaxDateFormattersTest extends Specification {

    def "parse date time"() {
        setup:
        def dateTime = LocalDateTime.parse("2007-12-03T10:15:30")
        expect:
        dateTime == LocalDateTime.from(WiclaxDateFormatters.DATE_TIME_FORMATTER.parse("03-12-2007 10:15:30"))
    }

    def "format date time"() {
        setup:
        def dateTime = LocalDateTime.parse("2007-12-03T10:15:30.20")
        expect:
        "03-12-2007 10:15:30" == WiclaxDateFormatters.DATE_TIME_FORMATTER.format(dateTime)
    }

    def "format date time with millis"() {
        setup:
        def dateTime = LocalDateTime.parse("2007-12-03T10:15:30.20")
        expect:
        "03-12-2007 10:15:30.200" == WiclaxDateFormatters.DATE_TIME_WITH_MILLIS_FORMATTER.format(dateTime)
    }

    def "format time with millis"() {
        setup:
        def dateTime = LocalDateTime.parse("2007-12-03T10:15:30.20")
        expect:
        "10:15:30.200" == WiclaxDateFormatters.TIME_WITH_MILLIS_FORMATTER.format(dateTime)
    }
}
