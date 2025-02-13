package no.nav.tms.event.test.producer.to.gui

import io.ktor.client.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.gui.components.descriptionItems
import no.nav.tms.event.test.producer.to.kvittering.fetchEnkelSoknad
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUserFactory
import java.time.format.DateTimeFormatter


fun Route.soknadPage(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient
) {
    get("soknad/{id}") {
        val id = call.parameters["id"] ?: throw IllegalArgumentException("Parameter 'id' is missing")
        val token = IdportenUserFactory.createIdportenUser(call).tokenString
        val soknad =
            fetchEnkelSoknad(tokenFetcher, soknadskvitteringUrl, httpClient, token, id)


        call.respondHtmlContent(
            "Min side søknad test producer", {
                a(
                    href = "/",
                    classes = "navds-link navds-link--action tilbake-til-alle-soknader"
                ) {
                    +"Tilbake til alle søknader"
                }
                h1 { +"Detaljer om søknad" }
                dl("navds-form-summary__answers") {
                    unsafe { +descriptionItems("tittel", soknad.tittel) }
                    unsafe { +descriptionItems("temakode", soknad.temakode) }
                    unsafe { +descriptionItems("linkSoknad", soknad.linkSoknad) }
                    unsafe { +descriptionItems("skjemanummer", soknad.skjemanummer) }
                    unsafe { +descriptionItems("journalpostId", soknad.journalpostId) }
                    unsafe {
                        +descriptionItems(
                            "fristEttersending",
                            soknad.fristEttersending.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        )
                    }
                    unsafe {
                        +descriptionItems(
                            "dato opprettet",
                            soknad.opprettet.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                        )
                    }
                    div("navds-form-summary__answer") {
                        this@dl.dt("navds-label") { +"Vedlegg som er motatt" }
                        this@dl.dd("navds-form-summary__value navds-body-long navds-body-long--medium") {
                            div {
                                soknad.mottatteVedlegg.map { mottattVedlegg ->
                                    ul {
                                        li { +mottattVedlegg.tittel }
                                    }
                                }.ifEmpty { p { + "Ingen vedlegg" } }
                            }
                        }
                    }
                    div("navds-form-summary__answer") {
                        this@dl.dt("navds-label") { +"Vedlegg som mangler" }
                        this@dl.dd("navds-form-summary__value navds-body-long navds-body-long--medium") {
                            soknad.manglendeVedlegg.map { mottattVedlegg ->
                                ul {
                                    li {
                                        span { +mottattVedlegg.tittel }
                                        a(
                                            href = """/soknad/vedlegg/motta?soknadsId=${id}&vedleggsId=${mottattVedlegg.vedleggsId}
                                                &brukerErAvsender=${mottattVedlegg.brukerErAvsender}
                                                &tittel=${mottattVedlegg.tittel}
                                                &linkVedlegg=${mottattVedlegg.linkEttersending}""".filterNot(Char::isWhitespace),
                                            classes = "navds-link navds-link--action marker-som-mottatt"
                                        ) { +"Marker som mottatt" }
                                    }
                                }
                            }.ifEmpty { p { + "Ingen vedlegg" } }

                        }
                    }

                }
                h2("heading-andre-eventer"){ + "Andre eventer knyttet til denne soknaden" }
                ul{
                    li{
                        a(
                            href = """/soknad/ettersporsel/vedlegg?soknadsId=${id}""".filterNot(Char::isWhitespace),
                            classes = "navds-link navds-link--action etterspor-vedlegg"
                        ) { +"Etterspør vedlegg" }
                    }
                    li { a(
                        href = """/soknad/ferdigstill?soknadsId=${id}""".filterNot(Char::isWhitespace),
                        classes = "navds-link navds-link--action ferdigstill-soknad"
                    ) { +"Ferdigstill søknad" } }
                    li { a(
                        href = """/soknad/oppdater?soknadsId=${id}""".filterNot(Char::isWhitespace),
                        classes = "navds-link navds-link--action oppdater-soknad"
                    ) { +"Oppdater søknad" } }
                }

            }, Environment.navDecoratorenUrl
        )
    }
}

