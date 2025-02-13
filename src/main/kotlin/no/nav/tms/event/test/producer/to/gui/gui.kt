package no.nav.tms.event.test.producer.to.gui

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.tms.event.test.producer.to.kvittering.SoknadEventProducer
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.idporten.sidecar.LevelOfAssurance
import no.nav.tms.token.support.idporten.sidecar.idPorten

fun Application.gui(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient,
    soknadEventProducer: SoknadEventProducer
) {

    //TODO setup logger
    val log = KotlinLogging.logger { }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            log.error { "404: page not found" }
            call.respondText(text = "404: Page Not Found", status = status)
        }

        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respondRedirect("/oauth2/login")
        }

        //TODO handle ulike error-exceptions
        exception<Throwable> { call, cause ->
            log.warn(cause) { "Ukjent feil: $cause" }
            call.respond(HttpStatusCode.InternalServerError)
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
        authenticate() {
            startPage(tokenFetcher, soknadskvitteringUrl, httpClient)
            soknadPage(tokenFetcher, soknadskvitteringUrl, httpClient)
            mottattVedlegg(soknadEventProducer)
            opprettSoknad()
        }
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


