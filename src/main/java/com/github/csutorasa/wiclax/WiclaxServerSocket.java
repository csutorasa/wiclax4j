package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.clock.WiclaxClock;
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Server socket for Wiclax clients.
 */
public class WiclaxServerSocket implements Closeable {

    private final ServerSocket serverSocket;
    private final WiclaxProtocolOptions protocolOptions;

    /**
     * Creates a new server socket with the given options.
     *
     * @param protocolOptions protocol options
     * @throws IOException              if the ServerSocket creation fails
     * @throws IllegalArgumentException options does not include a valid defaultTCPPort
     */
    public WiclaxServerSocket(WiclaxProtocolOptions protocolOptions) throws IOException {
        this(new ServerSocket(Optional.ofNullable(protocolOptions.getDefaultTCPPort()).orElseThrow(() ->
                new IllegalArgumentException("Options does not include a valid defaultTCPPort"))), protocolOptions);
    }

    /**
     * Creates a new server socket with the given port and options.
     *
     * @param port            socket port
     * @param protocolOptions protocol options
     * @throws IOException if the ServerSocket creation fails
     */
    public WiclaxServerSocket(int port, WiclaxProtocolOptions protocolOptions) throws IOException {
        this(new ServerSocket(port), protocolOptions);
    }

    /**
     * Creates a new server socket from an existing socket and options.
     *
     * @param serverSocket    existing socket
     * @param protocolOptions protocol options
     */
    public WiclaxServerSocket(ServerSocket serverSocket, WiclaxProtocolOptions protocolOptions) {
        this.serverSocket = serverSocket;
        this.protocolOptions = protocolOptions;
    }

    /**
     * Listens for a client and accepts it.
     *
     * @return client connection
     * @throws IOException thrown if the {@link ServerSocket#accept()} throws an exception
     */
    public WiclaxClientConnection accept() throws IOException {
        Socket socket = serverSocket.accept();
        return new WiclaxClientConnection(socket, protocolOptions, new WiclaxClock());
    }

    /**
     * Listens for a client and accepts it with a custom client connection generator.
     *
     * @param generator client connection generator
     * @return client connection
     * @throws IOException thrown if the {@link ServerSocket#accept()} throws an exception
     */
    public WiclaxClientConnection accept(BiFunction<Socket, WiclaxProtocolOptions, WiclaxClientConnection> generator) throws IOException {
        Socket socket = serverSocket.accept();
        return generator.apply(socket, protocolOptions);
    }

    /**
     * See {@link ServerSocket#isBound()} for more information.
     *
     * @return true is the socker is bound to an address
     */
    public boolean isBound() {
        return serverSocket.isBound();
    }

    /**
     * See {@link ServerSocket#isClosed()} for more information.
     *
     * @return true if the socket is closed
     */
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
