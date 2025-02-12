package no.nav.tms.event.test.producer.to.gui

import kotlinx.html.stream.createHTML
import no.nav.tms.event.test.producer.to.kvittering.fetchEnkelSoknad

import io.ktor.client.*
import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUserFactory
import java.time.format.DateTimeFormatter


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
                dl {
                    dt { + "tittel"}
                    dd { + (soknad.tittel) }
                    unsafe {descriptionItems("tittel", soknad.tittel)}
                    unsafe {descriptionItems("temakode", soknad.temakode)}
                    unsafe {descriptionItems("linkSoknad", soknad.linkSoknad)}
                    unsafe {descriptionItems("skjemanummer", soknad.skjemanummer)}
                    unsafe {descriptionItems("journalpostId", soknad.journalpostId)}
                    unsafe {descriptionItems("fristEttersending", soknad.fristEttersending.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss z")))}
                    unsafe {descriptionItems("dato opprettet", soknad.opprettet.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss z")))}
                }

            }, Environment.navDecoratorenUrl
        )
    }
}



fun descriptionItems (label: String, value: String?) {createHTML().run{
    dt { + label}
    dd { + (value ?: "null") }
}}
