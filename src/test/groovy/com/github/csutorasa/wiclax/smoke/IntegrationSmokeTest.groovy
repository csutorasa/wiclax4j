package com.github.csutorasa.wiclax.smoke

import com.github.csutorasa.wiclax.DefaultWiclaxClientReader
import com.github.csutorasa.wiclax.WiclaxClientConnection
import com.github.csutorasa.wiclax.WiclaxServerSocket
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import com.github.csutorasa.wiclax.heartbeat.DefaultWiclaxHeartbeatWriter
import com.github.csutorasa.wiclax.message.AcquisitionMessage
import com.github.csutorasa.wiclax.message.RaceStartMessage
import com.github.csutorasa.wiclax.model.Acquisition
import com.github.csutorasa.wiclax.requesthandler.RewindHandler
import spock.lang.Ignore
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.time.Instant

class IntegrationSmokeTest extends Specification {

    PollingConditions conditions = new PollingConditions()
    WiclaxProtocolOptions options = Options.options
    WiclaxServerSocket wiclaxServerSocket = new WiclaxServerSocket(options)

    @Ignore
    def "smoke test"() {
        given:
        int rewindCalls = 0
        RewindHandler rewindHandler = { from, to -> rewindCalls++ }
        WiclaxClientConnection connection = wiclaxServerSocket.accept()
        when:
        connection.startReading(new DefaultWiclaxClientReader(rewindHandler))
        connection.startHeartbeatWriting(new DefaultWiclaxHeartbeatWriter())
        then:
        conditions.within(60) {
            connection.readStarted
        }
        when:
        Acquisition acquisition1 = Acquisition.builder().deviceId("301").chipId("123").detectionTime(Instant.now()).build()
        connection.send(new AcquisitionMessage(acquisition1))
        Acquisition acquisition2 = Acquisition.builder().deviceId("302").chipId("123").detectionTime(Instant.now()).build()
        connection.send(new AcquisitionMessage(acquisition2))
        then:
        conditions.within(60) {
            !connection.readStarted
        }
        when:
        connection.send(new RaceStartMessage(Instant.now()))
        then:
        conditions.within(60) {
            assert rewindCalls >= 1
        }
        when:
        Acquisition acquisitionRewind1 = Acquisition.builder().deviceId("301").chipId("123").detectionTime(Instant.now()).rewind(true).build()
        connection.send(new AcquisitionMessage(acquisitionRewind1))
        Acquisition acquisitionRewind2 = Acquisition.builder().deviceId("302").chipId("123").detectionTime(Instant.now()).rewind(true).build()
        connection.send(new AcquisitionMessage(acquisitionRewind2))
        then:
        noExceptionThrown()
    }
}
