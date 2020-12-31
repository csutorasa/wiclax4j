# Wiclax4j ![Build wiclax4j](https://github.com/csutorasa/wiclax4j/workflows/Build%20wiclax4j/badge.svg) [![Maven Central](https://img.shields.io/maven-central/v/com.github.csutorasa.wiclax4j/wiclax4j.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.csutorasa.wiclax4j%22%20AND%20a:%22wiclax4j%22)

This is a pure java implementation to create a generic acquisition [Wiclax](https://www.wiclax.com/) server. It has no
runtime dependencies and is built with Java 8.

## How to use

Check the latest version, and import the library as dependency
from [here](https://search.maven.org/artifact/com.github.csutorasa.wiclax4j/wiclax4j).

### Example

```java
package org.example;

import com.github.csutorasa.wiclax.DefaultWiclaxClientReader;
import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxServerSocketTest;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.heartbeat.DefaultWiclaxHeartbeatWriter;
import com.github.csutorasa.wiclax.message.AcquisitionMessage;
import com.github.csutorasa.wiclax.model.Acquisition;

import java.io.IOException;
import java.time.Instant;

public class WiclaxExample {
    public static void main(String... args) throws IOException, InterruptedException {
        // Create protocol options
        WiclaxProtocolOptions options = WiclaxProtocolOptions.defaults();
        // Create a server socket with port 12345
        WiclaxServerSocket wiclaxServerSocket = new WiclaxServerSocket(12345, options);
        // Accept a client connection
        WiclaxClientConnection connection = wiclaxServerSocket.accept();
        // Start reading requests from the client
        connection.startReading(new DefaultWiclaxClientReader(WiclaxExample::rewind));
        // Start writing heartbeat messages
        connection.startHeartbeatWriting(new DefaultWiclaxHeartbeatWriter());
        // Send an acquisition
        Acquisition acquisition = Acquisition.builder().deviceId("301").chipId("123").detectionTime(Instant.now()).build();
        connection.send(new AcquisitionMessage(acquisition));
        // Do some work
        Thread.sleep(15000);
        // Close connections, preferably in finally clause.
        connection.close();
        wiclaxServerSocket.close();
    }

    public static void rewind(Instant from, Instant to) {
        // Handle rewind requests
    }
}
```

## How it works

First it is recommended to get to know to the [protocol specification](docs/protocol.md)
and [custom protocol options and configuration](docs/acquisitiontype.md).

### Reader

To read from clients you can use any implementation
of [WiclaxClientReader](src/main/java/com/github/csutorasa/wiclax/WiclaxClientReader.java). There is a default
implementation [DefaultWiclaxClientReader](src/main/java/com/github/csutorasa/wiclax/DefaultWiclaxClientReader.java),
but you can customize it by handling the errors correctly. As there are no dependencies, there are no logging is
included in the project.

### Requests

Requests need to be parsed first. [Request parsers](src/main/java/com/github/csutorasa/wiclax/requestparser)
are extending
the [WiclaxRequestParser](src/main/java/com/github/csutorasa/wiclax/requestparser/WiclaxRequestParser.java).

Parsers create [requests](src/main/java/com/github/csutorasa/wiclax/request) that extend the
[WiclaxRequest](src/main/java/com/github/csutorasa/wiclax/request/WiclaxRequest.java). These objects store the
information about the request.

After the request is parsed [request handlers](src/main/java/com/github/csutorasa/wiclax/request) must be added that
extend [WiclaxRequestHandler](src/main/java/com/github/csutorasa/wiclax/requesthandler/WiclaxRequestHandler.java). They
must describe what requests they can support. It can return a response which will be sent to the client.

### Responses

All [responses](src/main/java/com/github/csutorasa/wiclax/response) need to implement
the [WiclaxResponse](src/main/java/com/github/csutorasa/wiclax/response/WiclaxResponse.java). To create a new response
you need to override the `toData()` method and return the text to be sent to the client.

### Messages

All [messages](src/main/java/com/github/csutorasa/wiclax/message) need to implement
the [WiclaxMessage](src/main/java/com/github/csutorasa/wiclax/message/WiclaxMessage.java). To create a new message you
need to override the `toData(WiclaxProtocolOptions)` method and return the text to be sent to the client.

### Clock

Wiclax can synchronize the clock by sending a message to the server. The server must accept it as the current time, and
show all dates and times according to that time shift. By default, all connections have
separate [clocks](src/main/java/com/github/csutorasa/wiclax/clock/WiclaxClock.java).
