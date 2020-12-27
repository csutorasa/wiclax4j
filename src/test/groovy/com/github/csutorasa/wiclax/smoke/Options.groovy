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
            .inCommandEndChars('\n')
            .build()
    private static allCustomOptions = WiclaxProtocolOptions.builder()
            .defaultTCPPort(12345)
            .withHeartbeat(true)
            .heartbeatValue("_")
            .passingDataSeparator(',')
            .passingDataMask(null)
            .inCommandEndChars('\n')
            .outCommandEndChars('\n')
            .commandsForInitialization('HELLO WORLD')
            .getClockCommand('GETCLOCK')
            .setClockCommand('SETCLOCK YYYYMMDDhhmmssccc')
            .rewindCommand('REWIND')
            .startReadCommand('STARTREADING')
            .stopReadCommand('STOPREADING')
            .build()

    static WiclaxProtocolOptions options = standardOptions
}
