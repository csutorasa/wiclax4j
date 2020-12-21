# Wiclax4j ![Build wiclax4j](https://github.com/csutorasa/wiclax4j/workflows/Build%20wiclax4j/badge.svg) [![Maven Central](https://img.shields.io/maven-central/v/com.github.csutorasa.wiclax4j/wiclax4j.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.csutorasa.wiclax4j%22%20AND%20a:%22wiclax4j%22)

This is a pure java implementation to create a generic acquisition [Wiclax](https://www.wiclax.com/) server. It has no
runtime dependencies and is built with Java 8.

## How to use

Check the latest version, and import the library as dependency
from [here](https://search.maven.org/artifact/com.github.csutorasa.wiclax4j/wiclax4j).

### Example

```java
package org.example;

import java.io.IOException;
import java.time.Instant;

import com.github.csutorasa.wiclax.DefaultWiclaxClientReader;
import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxServerSocket;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.HeartBeatMessage;

public class WiclaxExample {

    public static void main(String... args) throws IOException, InterruptedException {
        // Create protocol options
        WiclaxProtocolOptions options = WiclaxProtocolOptions.DEFAULT_OPTIONS;
        // Create a server socket with port 12345
        WiclaxServerSocket wiclaxServerSocket = new WiclaxServerSocket(12345, options);
        // Accept a client connection
        WiclaxClientConnection connection = wiclaxServerSocket.accept();
        // Start reading requests from the client
        connection.startReading(new DefaultWiclaxClientReader(WiclaxExample::rewind));
        // Send a message to the client
        connection.send(new HeartBeatMessage(options));
        // Do some other work
        Thread.sleep(30000);
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

### Requests

All requests, that you would like to handle, need [request handlers](src/main/java/com/github/csutorasa/wiclax/request).
To create a new handler you need to create a subclass
of [WiclaxRequestHandler](src/main/java/com/github/csutorasa/wiclax/request/WiclaxRequestHandler.java). Overriding
the `supports(String command, String data)` allows handling the requests.

#### Reader

To read from clients you can use any implementation
of [WiclaxClientReader](src/main/java/com/github/csutorasa/wiclax/WiclaxClientReader.java). There is a default
implementation [DefaultWiclaxClientReader](src/main/java/com/github/csutorasa/wiclax/DefaultWiclaxClientReader.java),
but you can customize it by handling the errors correctly. As there are no dependencies, there are no logging is
included in the project.

### Messages and responses

All [messages and responses](src/main/java/com/github/csutorasa/wiclax/message) need to extend
the [WiclaxMessage](src/main/java/com/github/csutorasa/wiclax/message/WiclaxMessage.java). To create a new message or
response you need to override the `toData()` method and return the text to be sent to the client.

### Clock

Wiclax can synchronize the clock by sending a message to the server. The server must accept it as the current time, and
show all dates and times according to that time shift. By default, all connections have
separate [clocks](src/main/java/com/github/csutorasa/wiclax/clock/WiclaxClock.java).
