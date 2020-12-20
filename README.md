# Wiclax4j ![Build wiclax4j](https://github.com/csutorasa/wiclax4j/workflows/Build%20wiclax4j/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.csutorasa.wiclax4j/wiclax4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.wiclax4j/wiclax4j)

This is a pure java implementation to create a generic [Wiclax](https://www.wiclax.com/) server.
It has no runtime dependencies and is built with Java 8.

## How to use

Maven:

```xml
<project>
    ...
    <depencencies>    
        <dependency>
            <groupId>com.github.csutorasa.wiclax4j</groupId>
            <artifactId>wiclax4j</artifactId>
            <version>1.0.0</version>
        </dependency>
    </depencencies>
</project>
```

Gradle:

```groovy
dependencies {
    implementation 'com.github.csutorasa.wiclax4j:wiclax4j:1.0.0'
}
```

### Example

```java
package org.example;

import java.io.IOException;
import java.time.Instant;

import com.github.csutorasa.wiclax.DefaultWiclaxClientReader;
import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.WiclaxServerSocket;
import com.github.csutorasa.wiclax.message.HeartBeatMessage;

public class WiclaxExample {
    public static void main(String... args) throws IOException {
        // Create a server socket with port 12345
        WiclaxServerSocket wiclaxServerSocket = new WiclaxServerSocket(12345);
        // Accept a client connection
        WiclaxClientConnection connection = wiclaxServerSocket.accept();
        // Start reading requests from the client
        connection.startReading(new DefaultWiclaxClientReader(this::rewind));
        // Send a message to the client
        connection.send(new HeartBeatMessage());
        // Close connections, preferably in finally clause.
        connection.close();
        wiclaxServerSocket.close();
    }

    public void rewind(Instant from, Instant to) {
        // Handle rewind requests
    }
}
```

## How it works

### Requests

Requests are coming from the client in a `COMMAND [ARGUMENTS]` format.
All requests, that you would like to handle, need [request handlers](src/main/java/com/github/csutorasa/wiclax/request).
To create a new handler you need to create a subclass of [WiclaxRequestHandler](src/main/java/com/github/csutorasa/wiclax/request/WiclaxRequestHandler.java).
Overriding the `supports(String command, String data)` allows handling the requests.

#### Reader

To read from clients you can use any implementation of [WiclaxClientReader](src/main/java/com/github/csutorasa/wiclax/WiclaxClientReader.java).
There is a default implementation [DefaultWiclaxClientReader](src/main/java/com/github/csutorasa/wiclax/DefaultWiclaxClientReader.java),
but you can customize it by handling the errors correctly.
As there are no dependencies, there are no logging is included in the project.

### Messages and responses

Messages are not in a strict format, however responses have a similar `RESPONSE` format.
All [messages and responses](src/main/java/com/github/csutorasa/wiclax/message) need to extend the [WiclaxMessage](src/main/java/com/github/csutorasa/wiclax/message/WiclaxMessage.java).
To create a new message or response you need to override the `toData()` method and return the text to be sent to the client.

### Clock

Wiclax can synchronize the clock by sending a message to the server.
The server must accept it as the current time, and show all dates and times according to that time shift.
By default, all connections have separate [clocks](src/main/java/com/github/csutorasa/wiclax/clock/WiclaxClock.java).
