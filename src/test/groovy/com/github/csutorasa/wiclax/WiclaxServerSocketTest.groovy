package com.github.csutorasa.wiclax

import com.github.csutorasa.wiclax.config.WiclaxProtocolOptions
import spock.lang.Specification

class WiclaxServerSocketTest extends Specification {

    WiclaxServerSocket wiclaxServerSocket

    def "cannot create without port"() {
        when:
        wiclaxServerSocket = new WiclaxServerSocket(WiclaxProtocolOptions.defaults())
        then:
        thrown(IllegalArgumentException)
    }
}
