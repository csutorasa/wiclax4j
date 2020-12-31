package com.github.csutorasa.wiclax.smoke

import com.github.csutorasa.wiclax.config.WiclaxAcquisitionConfiguration

class AcquisitionTypeGenerator {

    // static OutputStream outputStream = System.out
    static OutputStream outputStream = new FileOutputStream("smoketest.chip-acquisition.xml")

    static void main(String... args) {
        def data = AcquisitionTypeGenerator.class.getClassLoader().getResourceAsStream("icons/testicon.png").getBytes()
        WiclaxAcquisitionConfiguration acquisitionConfiguration = new WiclaxAcquisitionConfiguration(Options.options, "Smoke test", data)
        acquisitionConfiguration.writeTo(new OutputStreamWriter(outputStream))
    }
}
