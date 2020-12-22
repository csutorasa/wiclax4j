package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;
import com.github.csutorasa.wiclax.message.HeartBeatMessage;
import com.github.csutorasa.wiclax.message.WiclaxMessage;
import com.github.csutorasa.wiclax.response.WiclaxResponse;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Single connection to a Wiclax client.
 */
public class WiclaxClientConnection implements Closeable {

    private static final String DEFAULT_IN_COMMAND_END_CHARS = "\r";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Getter
    private final WiclaxClock clock;
    @Getter
    private final WiclaxProtocolOptions protocolOptions;
    private final Socket socket;
    private final BufferedReader inputStream;
    private final BufferedWriter outputStream;
    private final AtomicBoolean readStarted = new AtomicBoolean(false);

    /**
     * Creates a new client connection
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
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
    }

    /**
     * Starts reading from the client and processes the messages.
     *
     * @param clientReader client reading logic
     */
    public void startReading(WiclaxClientReader clientReader) {
        clientReader.startRead(socket, inputStream, this, () -> readStarted.set(true), () -> readStarted.set(false));
    }

    /**
     * Sends message to Wiclax.
     * This method is synchronized to the class, so messages from different threads will not conflict.
     *
     * @param message message to be sent
     * @throws IOException thrown if the underlying stream throws an exception
     */
    public synchronized void send(WiclaxMessage message) throws IOException {
        outputStream.write(message.toData(protocolOptions));
        outputStream.write(protocolOptions.get(WiclaxProtocolOptions::getInCommandEndChars).orElse(DEFAULT_IN_COMMAND_END_CHARS));
        outputStream.flush();
    }

    synchronized void sendResponse(WiclaxResponse response) {
        try {
            outputStream.write(response.toData());
            outputStream.write(protocolOptions.get(WiclaxProtocolOptions::getInCommandEndChars).orElse(DEFAULT_IN_COMMAND_END_CHARS));
            outputStream.flush();
        } catch (IOException e) {
            // Ignored
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
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
