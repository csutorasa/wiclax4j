package com.github.csutorasa.wiclax.smoke

import com.github.csutorasa.wiclax.config.WiclaxAcquisitionConfiguration
import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions

class AcquisitionTypeGenerator {

    static WiclaxProtocolOptions options = WiclaxProtocolOptions.builder()
            .defaultTCPPort(12345)
            .withHeartbeat(true)
            .build()

    static OutputStream outputStream = System.out
    // static OutputStream outputStream = new FileOutputStream("smoketest.chip-acquisition.xml")

    static void main(String... args) {
        WiclaxAcquisitionConfiguration acquisitionConfiguration = WiclaxAcquisitionConfiguration.fromOptions(options, "Smoke test", null)
        acquisitionConfiguration.writeTo(new OutputStreamWriter(outputStream))
    }
}
