package no.nav.tms.event.test.producer.to.gui.pages

import io.ktor.client.*
import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.gui.respondHtmlContent
import no.nav.tms.event.test.producer.to.kvittering.fetchKvitteringer
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUserFactory


fun Route.startPage(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient
) {
    get {
        val token = IdportenUserFactory.createIdportenUser(call).tokenString
        val soknadsKvitteringer =
            fetchKvitteringer(tokenFetcher, soknadskvitteringUrl, httpClient, token)

        call.respondHtmlContent(
            "Min side søknad test producer", {
                h1 { +"Soknad event producer" }
                p { +"Dette er et internt grensesnitt for testing. Her kan du hente søknader og endre eksisterende søknader med Kafka-eventer." }
                h2("heading-soknadsliste") { +"Liste med soknadskvitteringer" }
                if (soknadsKvitteringer.isEmpty()) {
                    p { +"Ingen soknader på denne brukeren" }
                } else {
                    ul {
                        soknadsKvitteringer.map { kvittering ->
                            li {
                                a(href = "/soknad/${kvittering.soknadsId}") {
                                    +kvittering.tittel
                                }
                            }
                        }
                    }
                }
                a(
                    classes = "navds-link navds-link--action opprett-soknad",
                    href = "/soknad/opprett"
                ) { +"Opprett ny søknad" }

            }, Environment.navDecoratorenUrl
        )
    }
}