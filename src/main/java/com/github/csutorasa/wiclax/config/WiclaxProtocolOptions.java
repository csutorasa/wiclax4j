package com.github.csutorasa.wiclax.config;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Function;

/**
 * Acquisition protocol options.
 */
@Getter
@Builder
public class WiclaxProtocolOptions {

    /**
     * Default generic options.
     */
    public static final WiclaxProtocolOptions DEFAULT_OPTIONS = WiclaxProtocolOptions.builder().build();

    /**
     * Default generic options.
     *
     * @return protocol options
     */
    public static WiclaxProtocolOptions defaults() {
        return DEFAULT_OPTIONS;
    }

    /**
     * Default port to use for incoming connections.
     */
    private final Integer defaultTCPPort;
    /**
     * If the server sends heartbeat messages.
     */
    private final Boolean withHeartbeat;
    /**
     * Heartbeat text value.
     */
    private final String heartbeatValue;
    /**
     * Acquisition passing data separator.
     */
    private final String passingDataSeparator;
    /**
     * Acquisition passing data format.
     */
    private final String passingDataMask;
    /**
     * End of command sent by the server.
     */
    private final String inCommandEndChars;
    /**
     * End of command sent by the client.
     */
    private final String outCommandEndChars;
    /**
     * Initialization command.
     */
    private final String commandsForInitialization;
    /**
     * Get clock command.
     */
    private final String getClockCommand;
    /**
     * Get clock response.
     */
    private final String getClockResponse;
    /**
     * Set clock command.
     */
    private final String setClockCommand;
    /**
     * Review command.
     */
    private final String rewindCommand;
    /**
     * Start read command.
     */
    private final String startReadCommand;
    /**
     * Stop read command.
     */
    private final String stopReadCommand;

    /**
     * Creates new protocol options.
     *
     * @param defaultTCPPort            default port to use for incoming connections
     * @param withHeartbeat             if the server sends heartbeat messages
     * @param heartbeatValue            heartbeat text value
     * @param passingDataSeparator      acquisition passing data separator
     * @param passingDataMask           acquisition passing data format
     * @param inCommandEndChars         end of command sent by the server
     * @param outCommandEndChars        end of command sent by the client
     * @param commandsForInitialization initialization command
     * @param getClockCommand           get clock command
     * @param getClockResponse          get clock response
     * @param setClockCommand           set clock command
     * @param rewindCommand             rewind command
     * @param startReadCommand          start read command
     * @param stopReadCommand           stop read command
     */
    public WiclaxProtocolOptions(Integer defaultTCPPort, Boolean withHeartbeat, String heartbeatValue,
                                 String passingDataSeparator, String passingDataMask, String inCommandEndChars,
                                 String outCommandEndChars, String commandsForInitialization, String getClockCommand,
                                 String getClockResponse, String setClockCommand, String rewindCommand,
                                 String startReadCommand, String stopReadCommand) {
        this.defaultTCPPort = defaultTCPPort;
        this.withHeartbeat = withHeartbeat;
        this.heartbeatValue = heartbeatValue;
        if (passingDataMask != null && passingDataSeparator == null) {
            throw new IllegalArgumentException("PassingDataSeparator cannot be null if the passingDataMask is provided.");
        }
        this.passingDataSeparator = passingDataSeparator;
        if (passingDataMask != null && !passingDataMask.contains("C")) {
            throw new IllegalArgumentException("PassingDataMask must contain chip id.");
        }
        this.passingDataMask = passingDataMask;
        if (inCommandEndChars == null) {
            this.inCommandEndChars = null;
        } else if ("\\r".equals(inCommandEndChars)) {
            this.inCommandEndChars = "\r";
        } else if ("\\n".equals(inCommandEndChars)) {
            this.inCommandEndChars = "\n";
        } else if ("\\r\\n".equals(inCommandEndChars)) {
            this.inCommandEndChars = "\r\n";
        } else {
            throw new IllegalArgumentException("InCommandEndChars must be \\r, \\n or \\r\\n.");
        }
        this.outCommandEndChars = outCommandEndChars;
        this.commandsForInitialization = commandsForInitialization;
        this.getClockCommand = getClockCommand;
        this.getClockResponse = getClockResponse;
        this.setClockCommand = setClockCommand;
        this.rewindCommand = rewindCommand;
        this.startReadCommand = startReadCommand;
        this.stopReadCommand = stopReadCommand;
    }

    /**
     * Creates new protocol options.
     *
     * @param protocolOptions existing protocol options
     */
    protected WiclaxProtocolOptions(WiclaxProtocolOptions protocolOptions) {
        defaultTCPPort = protocolOptions.defaultTCPPort;
        withHeartbeat = protocolOptions.withHeartbeat;
        heartbeatValue = protocolOptions.heartbeatValue;
        passingDataSeparator = protocolOptions.passingDataSeparator;
        passingDataMask = protocolOptions.passingDataMask;
        inCommandEndChars = protocolOptions.inCommandEndChars;
        outCommandEndChars = protocolOptions.outCommandEndChars;
        commandsForInitialization = protocolOptions.commandsForInitialization;
        getClockCommand = protocolOptions.getClockCommand;
        getClockResponse = protocolOptions.getClockResponse;
        setClockCommand = protocolOptions.setClockCommand;
        rewindCommand = protocolOptions.rewindCommand;
        startReadCommand = protocolOptions.startReadCommand;
        stopReadCommand = protocolOptions.stopReadCommand;
    }

    /**
     * Get optional value from the options. Makes using default values easier.
     *
     * @param getter value getter
     * @param <T>    type of value
     * @return {@link Optional#empty()} or the value if it is present
     */
    public <T> Optional<T> get(Function<WiclaxProtocolOptions, T> getter) {
        T value = getter.apply(this);
        return Optional.ofNullable(value);
    }
}
