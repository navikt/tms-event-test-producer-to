package no.nav.tms.event.test.producer.to.gui

import io.ktor.client.*
import io.ktor.server.routing.*
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.p
import no.nav.tms.event.test.producer.to.kvittering.fetchKvitteringer
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUserFactory


fun Route.startPage(
    navDecoratorenUrl: String,
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
                h1 { +"Opprett soknadskvittering" }
                p { +"Her kan du opprette og redigere søknadseventer for testing på Min side. " }
                h2 { +"Liste med soknadskvitteringer" }
                p { +"${soknadsKvitteringer.toString()}" }
            }, navDecoratorenUrl
        )
    }
}