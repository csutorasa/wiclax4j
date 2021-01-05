package com.github.csutorasa.wiclax.heartbeat;

import com.github.csutorasa.wiclax.WiclaxClientConnection;
import com.github.csutorasa.wiclax.exception.ErrorHandler;
import com.github.csutorasa.wiclax.message.MessageSender;
import lombok.RequiredArgsConstructor;

/**
 * Default heartbeat writer with its own thread.
 */
@RequiredArgsConstructor
public class DefaultWiclaxHeartbeatWriterThread extends AbstractWiclaxHeartbeatWriter {

    private final long intervalMillis;
    private final ErrorHandler<Exception> unhandledSendingException;
    private final ErrorHandler<Throwable> threadException;

    /**
     * Creates a new writer.
     */
    public DefaultWiclaxHeartbeatWriterThread() {
        this(5000);
    }

    /**
     * Creates a new writer with custom interval.
     *
     * @param intervalMillis interval between heartbeats in milliseconds
     */
    public DefaultWiclaxHeartbeatWriterThread(long intervalMillis) {
        this.intervalMillis = intervalMillis;
        unhandledSendingException = (exception) -> {
        };
        threadException = ErrorHandler.rethrow();
    }

    @Override
    public void startWrite(WiclaxClientConnection clientConnection, MessageSender messageSender) {
        Thread writerThread = new Thread(() -> writer(messageSender));
        writerThread.setName("Wiclax heartbeat writer for " + clientConnection.getRemoteSocketAddress().toString());
        writerThread.start();
    }

    private void writer(MessageSender messageSender) {
        boolean exit = false;
        while (!exit) {
            exit = ErrorHandler.runWithHandler(() -> send(messageSender), threadException, false);
        }
    }

    private boolean send(MessageSender messageSender) throws InterruptedException {
        try {
            sendHeartbeatMessage(messageSender);
        } catch (Exception e) {
            unhandledSendingException.handle(e);
        }
        Thread.sleep(intervalMillis);
        return false;
    }
}
