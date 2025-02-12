package no.nav.tms.event.test.producer.to.gui

import io.ktor.server.html.*
import kotlinx.html.stream.createHTML
import no.nav.tms.event.test.producer.to.kvittering.fetchEnkelSoknad

import io.ktor.client.*
import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUserFactory
import kotlinx.html.dt
import kotlinx.html.dd

fun Route.soknadPage(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient
) {
    get("soknad/{id}"){
        val id = call.parameters["id"]?: throw IllegalArgumentException("Parameter 'id' is missing")
        val token = IdportenUserFactory.createIdportenUser(call).tokenString
        val soknad =
            fetchEnkelSoknad(tokenFetcher, soknadskvitteringUrl, httpClient, token, id)


        call.respondHtmlContent(
            "Min side søknad test producer", {
                h1 { +"Om søknaden" }
                listElement("tittel", soknad.tittel)
                listElement("temakode", soknad.temakode)
            }, Environment.navDecoratorenUrl
        )
    }
}



fun listElement (label: String, value: String?) {createHTML().dl{
    dt { + label}
    dd { + (value ?: "null") }
}}