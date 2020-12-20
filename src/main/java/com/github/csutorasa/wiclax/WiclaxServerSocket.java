package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.clock.WiclaxClock;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server socket for Wiclax clients.
 */
public class WiclaxServerSocket implements Closeable {

    private final ServerSocket serverSocket;

    /**
     * Creates a new server socket with the given port
     * @param port socket port
     * @throws IOException if the ServerSocket creation fails
     */
    public WiclaxServerSocket(int port) throws IOException {
        this(new ServerSocket(port));
    }

    /**
     * Creates a new server socket from an existing socket.
     * @param serverSocket existing socket
     */
    public WiclaxServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Listens for a client and accepts it.
     * @return client connection
     * @throws IOException thrown if the {@link ServerSocket#accept()} throws an exception
     */
    public WiclaxClientConnection accept() throws IOException {
        Socket socket = serverSocket.accept();
        return new WiclaxClientConnection(socket, new WiclaxClock());
    }

    /**
     * Listens for a client and accepts it. Creates the connection with a custom clock.
     *
     * @param clock custom clock
     * @return client connection
     * @throws IOException thrown if the {@link ServerSocket#accept()} throws an exception
     */
    public WiclaxClientConnection accept(WiclaxClock clock) throws IOException {
        Socket socket = serverSocket.accept();
        return new WiclaxClientConnection(socket, clock);
    }

    /**
     * See {@link ServerSocket#isBound()} for more information.
     * @return true is the socker is bound to an address
     */
    public boolean isBound() {
        return serverSocket.isBound();
    }

    /**
     * See {@link ServerSocket#isClosed()} for more information.
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
