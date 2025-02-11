package no.nav.tms.event.test.producer.to

import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.tms.common.util.config.IntEnvVar.getEnvVarAsInt
import no.nav.tms.event.test.producer.to.gui.gui


fun main() {    embeddedServer(
    Netty,
    port = getEnvVarAsInt("PORT", 8081),
    module = {
        gui()
    }
).start(wait = true)
}
