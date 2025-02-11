package no.nav.tms.event.test.producer.to.gui

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.tms.token.support.idporten.sidecar.LevelOfAssurance
import no.nav.tms.token.support.idporten.sidecar.idPorten

fun Application.gui() {

    //TODO setup logger
    val log = KotlinLogging.logger { }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }

        //TODO handle ulike error-exceptions
        exception<Throwable> { call, cause ->
            log.error(cause) { "Ukjent feil" }
        }
    }

    authentication {
        idPorten {
            setAsDefault = true
            levelOfAssurance = LevelOfAssurance.SUBSTANTIAL
        }
    }

    routing {
        meta()

            startPage()
        staticResources("/static", "static") {
            preCompressed(CompressedFileType.GZIP)
        }
    }
}

fun Routing.meta() {
    get("isalive") {
        call.respond(HttpStatusCode.OK)
    }
    get("isready") {
        call.respond(HttpStatusCode.OK)
    }
}


