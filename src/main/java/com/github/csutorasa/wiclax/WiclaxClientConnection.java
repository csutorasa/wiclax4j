package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.heartbeat.WiclaxHeartbeatWriter;
import com.github.csutorasa.wiclax.message.WiclaxMessage;
import com.github.csutorasa.wiclax.reader.WiclaxClientReader;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Single connection to a Wiclax client.
 */
public class WiclaxClientConnection implements Closeable {

    private static final String DEFAULT_IN_COMMAND_END_CHARS = "\r";
    private static final String DEFAULT_OUT_COMMAND_END_CHARS = "\r";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Getter
    private final WiclaxClock clock;
    @Getter
    private final WiclaxProtocolOptions protocolOptions;
    private final Socket socket;
    private final Scanner inputScanner;
    private final Writer outputStream;
    private final AtomicBoolean readStarted = new AtomicBoolean(false);
    private final Object writeLock = new Object();
    private final Object readLock = new Object();

    /**
     * Creates a new client connection.
     *
     * @param socket          client socket
     * @param protocolOptions protocol options
     * @throws IOException if the socket or stream throws and exception
     */
    public WiclaxClientConnection(Socket socket, WiclaxProtocolOptions protocolOptions) throws IOException {
        this(socket, protocolOptions, new WiclaxClock());
    }

    /**
     * Creates a new client connection.
     *
     * @param socket          client socket
     * @param protocolOptions protocol options
     * @param clock           clock to be used
     * @throws IOException if the socket or stream throws and exception
     */
    public WiclaxClientConnection(Socket socket, WiclaxProtocolOptions protocolOptions, WiclaxClock clock) throws IOException {
        this.socket = socket;
        this.protocolOptions = protocolOptions;
        this.clock = clock;
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
        String delimiter = protocolOptions.get(WiclaxProtocolOptions::getOutCommandEndChars).orElse(DEFAULT_OUT_COMMAND_END_CHARS);
        inputScanner = new Scanner(reader);
        inputScanner.useDelimiter(Pattern.compile(delimiter));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
    }

    /**
     * Starts reading from the client and processes the messages.
     *
     * @param clientReader client reading logic
     */
    public void startReading(WiclaxClientReader clientReader) {
        clientReader.startRead(this, this::read, this::send, () -> readStarted.set(true), () -> readStarted.set(false));
    }

    /**
     * Starts writing to the client.
     *
     * @param heartbeatWriter heartbeat writing logic
     */
    public void startHeartbeatWriting(WiclaxHeartbeatWriter heartbeatWriter) {
        heartbeatWriter.startWrite(this, this::send);
    }

    /**
     * Sends message to Wiclax.
     * This is a thread-safe method.
     *
     * @param message message to be sent
     * @throws IOException thrown if the underlying stream throws an exception
     */
    public void send(WiclaxMessage message) throws IOException {
        send(message.toData(protocolOptions));
    }

    private void send(WiclaxResponse response) throws IOException {
        send(response.toData());
    }

    private void send(String data) throws IOException {
        synchronized (writeLock) {
            outputStream.write(data);
            outputStream.write(protocolOptions.get(WiclaxProtocolOptions::getInCommandEndChars).orElse(DEFAULT_IN_COMMAND_END_CHARS));
            outputStream.flush();
        }
    }

    private String read() {
        if (socket.isClosed()) {
            return null;
        }
        synchronized (readLock) {
            if (inputScanner.hasNext()) {
                return inputScanner.next();
            } else {
                return null;
            }
        }
    }

    /**
     * Gets if the read is started in Wiclax.
     *
     * @return if the read is started
     */
    public boolean isReadStarted() {
        return readStarted.get();
    }

    /**
     * See {@link Socket#isClosed()}.
     *
     * @return if the socket is closed
     */
    public boolean isClosed() {
        return socket.isClosed();
    }

    /**
     * See {@link Socket#getRemoteSocketAddress()}.
     *
     * @return remote address
     */
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        inputScanner.close();
        outputStream.close();
    }
}
