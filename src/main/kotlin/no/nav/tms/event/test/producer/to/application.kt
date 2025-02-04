package no.nav.tms.event.test.producer.to

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main() {

}


fun Route.metaRoutes() {
    get("/internal/isAlive") {
        call.respondText(text = "ALIVE", contentType = ContentType.Text.Plain)
    }

    get("/internal/isReady") {
        call.respondText(text = "READY", contentType = ContentType.Text.Plain)
    }
}