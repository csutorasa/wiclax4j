package com.github.csutorasa.wiclax.formatter

import spock.lang.Specification
import spock.lang.Unroll

class BooleanToNumberFormatterTest extends Specification {

    @Unroll
    def "boolean #bool to number"(Boolean bool, String number) {
        expect:
        number == BooleanToNumberFormatter.format(bool)
        where:
        bool  | number
        true  | "1"
        false | "0"
        null  | null
    }

    @Unroll
    def "number #number to boolean"(String number, Boolean bool) {
        expect:
        bool == BooleanToNumberFormatter.parse(number)
        where:
        number | bool
        "1"    | true
        "0"    | false
        null   | null
    }
}
