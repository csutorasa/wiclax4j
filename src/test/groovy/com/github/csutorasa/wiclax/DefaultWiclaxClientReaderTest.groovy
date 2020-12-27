package com.github.csutorasa.wiclax

import com.github.csutorasa.wiclax.exception.ErrorHandler
import com.github.csutorasa.wiclax.exception.UnhandledRequestException
import com.github.csutorasa.wiclax.exception.UnparseableRequestException
import com.github.csutorasa.wiclax.request.RequestReader
import com.github.csutorasa.wiclax.request.WiclaxRequest
import com.github.csutorasa.wiclax.requesthandler.WiclaxRequestHandlers
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers
import com.github.csutorasa.wiclax.response.ResponseSender
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

import java.util.function.BiConsumer
import java.util.function.Consumer

class DefaultWiclaxClientReaderTest extends Specification {

    WiclaxRequestHandlers handlers = Mock()
    WiclaxRequestParsers parsers = Mock()
    RequestReader requestReader = Mock()
    ResponseSender responseSender = Mock()

    def "reading and processing works"() {
        given:
        String requestLine = "test request"
        WiclaxRequest request = new WiclaxRequest() {}
        def reader = new DefaultWiclaxClientReader(null)
        when:
        reader.readAndProcess(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> requestLine
        1 * parsers.parse(requestLine) >> request
        1 * handlers.handle(request) >> null
    }

    def "reading, processing and responding works"() {
        given:
        String requestLine = "test request"
        WiclaxRequest request = new WiclaxRequest() {}
        WiclaxResponse response = dummyResponse()
        def reader = new DefaultWiclaxClientReader(null)
        when:
        reader.readAndProcess(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> requestLine
        1 * parsers.parse(requestLine) >> request
        1 * handlers.handle(request) >> response
        1 * responseSender.send(response)
    }

    def "unhandled request exception handling works"() {
        given:
        String requestLine = "test request"
        WiclaxRequest request = new WiclaxRequest() {}
        Exception exception = new UnhandledRequestException(request)
        Consumer<WiclaxRequest> handler = Mock()
        def reader = new DefaultWiclaxClientReader(null, null, handler, null, null)
        when:
        reader.readAndProcess(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> requestLine
        1 * parsers.parse(requestLine) >> request
        1 * handlers.handle(request) >> {
            throw exception
        }
        1 * handler.accept(request)
    }

    def "unhandled request error exception handling works"() {
        given:
        String requestLine = "test request"
        WiclaxRequest request = new WiclaxRequest() {}
        Exception exception = new RuntimeException("test")
        ErrorHandler<Exception> handler = Mock()
        def reader = new DefaultWiclaxClientReader(null, null, null, handler, null)
        when:
        reader.readAndProcess(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> requestLine
        1 * parsers.parse(requestLine) >> request
        1 * handlers.handle(request) >> {
            throw exception
        }
        1 * handler.handle(exception)
    }

    def "unparseable request exception handling works"() {
        given:
        String requestLine = "test request"
        Exception exception = new UnparseableRequestException("test", "request")
        BiConsumer<String, String> handler = Mock()
        def reader = new DefaultWiclaxClientReader(null, handler, null, null, null)
        when:
        reader.readAndProcess(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> requestLine
        1 * parsers.parse(requestLine) >> {
            throw exception
        }
        1 * handler.accept("test", "request")
    }

    def "thread message exception handling works"() {
        given:
        Exception exception = new RuntimeException("test error")
        ErrorHandler<Throwable> handler = Mock()
        def reader = new DefaultWiclaxClientReader(null, null, null, ErrorHandler.rethrow(), handler)
        when:
        reader.readAndProcess(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> {
            throw exception
        }
        1 * handler.handle(exception)
    }

    def dummyResponse(String data = "") {
        return new WiclaxResponse() {
            @Override
            String toData() {
                return data
            }
        }
    }
}
