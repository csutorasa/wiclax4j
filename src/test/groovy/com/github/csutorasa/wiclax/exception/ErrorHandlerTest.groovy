package com.github.csutorasa.wiclax.exception

import spock.lang.Specification

class ErrorHandlerTest extends Specification {

    def "rethrow RuntimeException"() {
        given:
        ErrorHandler<Exception> errorHandler = ErrorHandler.rethrow()
        when:
        errorHandler.handle(new RuntimeException())
        then:
        thrown(RuntimeException)
    }

    def "rethrow Exception as RuntimeException"() {
        given:
        ErrorHandler<Exception> errorHandler = ErrorHandler.rethrow()
        when:
        errorHandler.handle(new Exception())
        then:
        thrown(RuntimeException)
    }
}
