package com.github.csutorasa.wiclax.reader

import com.github.csutorasa.wiclax.exception.ErrorHandler
import com.github.csutorasa.wiclax.exception.UnhandledRequestException
import com.github.csutorasa.wiclax.exception.UnparseableRequestException
import com.github.csutorasa.wiclax.reader.DefaultWiclaxClientReaderThread
import com.github.csutorasa.wiclax.request.RequestReader
import com.github.csutorasa.wiclax.request.WiclaxRequest
import com.github.csutorasa.wiclax.requesthandler.WiclaxRequestHandlers
import com.github.csutorasa.wiclax.requestparser.WiclaxRequestParsers
import com.github.csutorasa.wiclax.response.ResponseSender
import com.github.csutorasa.wiclax.response.WiclaxResponse
import spock.lang.Specification

import java.util.function.Consumer

class DefaultWiclaxClientReaderThreadTest extends Specification {

    WiclaxRequestHandlers handlers = Mock()
    WiclaxRequestParsers parsers = Mock()
    RequestReader requestReader = Mock()
    ResponseSender responseSender = Mock()

    def "reading and processing works"() {
        given:
        String requestLine = "test request"
        WiclaxRequest request = new WiclaxRequest() {}
        def reader = new DefaultWiclaxClientReaderThread(null)
        when:
        reader.processRequest(handlers, parsers, requestReader, responseSender)
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
        def reader = new DefaultWiclaxClientReaderThread(null)
        when:
        reader.processRequest(handlers, parsers, requestReader, responseSender)
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
        def reader = new DefaultWiclaxClientReaderThread(null, null, handler, null, null)
        when:
        reader.processRequest(handlers, parsers, requestReader, responseSender)
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
        def reader = new DefaultWiclaxClientReaderThread(null, null, null, handler, null)
        when:
        reader.processRequest(handlers, parsers, requestReader, responseSender)
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
        Exception exception = new UnparseableRequestException(requestLine)
        Consumer<String> handler = Mock()
        def reader = new DefaultWiclaxClientReaderThread(null, handler, null, null, null)
        when:
        reader.processRequest(handlers, parsers, requestReader, responseSender)
        then:
        1 * requestReader.readRequest() >> requestLine
        1 * parsers.parse(requestLine) >> {
            throw exception
        }
        1 * handler.accept(requestLine)
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
