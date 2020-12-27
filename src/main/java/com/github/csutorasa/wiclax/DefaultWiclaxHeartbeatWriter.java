package com.github.csutorasa.wiclax;

import com.github.csutorasa.wiclax.exception.ErrorHandler;
import com.github.csutorasa.wiclax.message.HeartBeatMessage;
import com.github.csutorasa.wiclax.message.MessageSender;
import lombok.RequiredArgsConstructor;

import java.net.Socket;

/**
 * Default reader implementation. The socket is read until it is closed.
 */
@RequiredArgsConstructor
public class DefaultWiclaxHeartbeatWriter extends WiclaxHeartbeatWriter {

    private final long intervalMillis;
    private final ErrorHandler<Exception> unhandledSendingException;
    private final ErrorHandler<Throwable> threadException;

    /**
     * Creates a new writer.
     */
    public DefaultWiclaxHeartbeatWriter() {
        this(5000);
    }

    /**
     * Creates a new writer with custom interval.
     *
     * @param intervalMillis interval between heartbeats in milliseconds
     */
    public DefaultWiclaxHeartbeatWriter(long intervalMillis) {
        this.intervalMillis = intervalMillis;
        unhandledSendingException = (exception) -> {
        };
        threadException = ErrorHandler.rethrow();
    }

    @Override
    public void startWrite(WiclaxClientConnection clientConnection, MessageSender messageSender) {
        Socket socket = clientConnection.getSocket();
        Thread writerThread = new Thread(() -> writer(socket, messageSender));
        writerThread.setName("Wiclax heartbeat writer for " + socket.getRemoteSocketAddress().toString());
        writerThread.start();
    }

    private void writer(Socket socket, MessageSender messageSender) {
        while (!socket.isClosed()) {
            try {
                sendMessage(messageSender);
                Thread.sleep(intervalMillis);
            } catch (Throwable t) {
                threadException(t);
            }
        }
    }

    private void sendMessage(MessageSender messageSender) {
        try {
            messageSender.send(new HeartBeatMessage());
        } catch (Exception e) {
            unhandledSendingException(e);
        }
    }

    /**
     * Dispatches the unhandled sending exceptions.
     *
     * @param exception any exception that happened during message sending
     */
    protected void unhandledSendingException(Exception exception) {
        unhandledSendingException.handle(exception);
    }

    /**
     * Last chance to stop the reader thread from being stopped.
     *
     * @param throwable any exception thrown by the thread
     */
    protected void threadException(Throwable throwable) {
        threadException.handle(throwable);
    }
}
