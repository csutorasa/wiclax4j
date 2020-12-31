package com.github.csutorasa.wiclax.smoke

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions

class Options {

    private static defaultOptions = WiclaxProtocolOptions.defaults()
    private static standardOptions = WiclaxProtocolOptions.builder()
            .defaultTCPPort(12345)
            .withHeartbeat(true)
            .build()
    private static workingOptions = WiclaxProtocolOptions.builder()
            .defaultTCPPort(12345)
            .withHeartbeat(true)
            .heartbeatValue("_")
            .inCommandEndChars('\\r\\n')
            .outCommandEndChars('\\r\\n')
            .commandsForInitialization('HELLO WORLD\r\n')
            .getClockCommand('GETCLOCK')
            .setClockCommand("'SETCLOCK' yyyyMMddHHmmss")
            .startReadCommand('STARTREADING')
            .stopReadCommand('STOPREADING')
            .build()
    private static allCustomOptions = WiclaxProtocolOptions.builder()
            .defaultTCPPort(12345)
            .withHeartbeat(true)
            .heartbeatValue("_")
            .passingDataSeparator(';')
            .passingDataMask('C;YYYY/MM/DD hh:mm:ss.ccc;@')
            .inCommandEndChars('\\n')
            .outCommandEndChars('\\n')
            .commandsForInitialization('HELLO WORLD\n')
            .getClockCommand('GETCLOCK')
            .getClockResponse('CLOCKTIME YYYYMMDDhhmmssccc')
            .setClockCommand("'SETCLOCK' yyyyMMddHHmmss")
            .rewindCommand("'REWIND' yyyyMMddHHmmss yyyyMMddHHmmss")
            .startReadCommand('STARTREADING')
            .stopReadCommand('STOPREADING')
            .build()

    static WiclaxProtocolOptions options = workingOptions
}
