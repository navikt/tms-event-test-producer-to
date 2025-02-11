package no.nav.tms.event.test.producer.to

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.tms.common.util.config.IntEnvVar.getEnvVarAsInt
import no.nav.tms.event.test.producer.to.gui.gui


fun main() {
    embeddedServer(
    Netty,
    port = getEnvVarAsInt("PORT", 8081),
    module = {
        gui()
    }
).start(wait = true)
}
