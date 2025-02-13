package no.nav.tms.event.test.producer.to.gui

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.a
import kotlinx.html.h1
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.kvittering.SoknadEventProducer
import no.nav.tms.event.test.producer.to.kvittering.SoknadRequest
import no.nav.tms.soknad.event.validation.SoknadsKvitteringValidationException
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUser
import no.nav.tms.token.support.idporten.sidecar.user.IdportenUserFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Route.opprettetSoknad(
    soknadEventProducer: SoknadEventProducer,
) {
    get("soknad/opprettet") {
        val log = KotlinLogging.logger {}
        val tittel = call.parameters["tittel"] ?: throw IllegalArgumentException("Parameter 'tittel' is missing")
        val temakode = call.parameters["temakode"] ?: throw IllegalArgumentException("Parameter 'temakode' is missing")
        val fristEttersending = call.parameters["fristEttersending"] ?: throw IllegalArgumentException("Parameter 'fristEttersending' is missing")
        val skjemanummer = call.parameters["skjemanummer"] ?: throw IllegalArgumentException("Parameter 'skjemanummer' is missing")
        val linkSoknad = getStringOrNull(call.parameters["linkSoknad"])
        val journalpostId = getStringOrNull(call.parameters["journalpostId"])
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val soknad = SoknadRequest.Opprett(
            tittel = tittel,
            temakode = temakode,
            linkSoknad = linkSoknad,
            skjemanummer = skjemanummer,
            journalpostId = journalpostId,
            fristEttersending =  LocalDate.parse(fristEttersending,formatter),
        )
        try {
            val soknadsId  = soknadEventProducer.opprettSoknad(IdportenUserFactory.createIdportenUser(call),soknad)
            log.info { "Soknad-opprettet event med id [$soknadsId] er lagt på kafka" }
        } catch (e: SoknadsKvitteringValidationException) {
            log.warn(e) { "Feilaktig innhold i opprett-request" }
            call.respond(HttpStatusCode.BadRequest)
        }
        call.respondHtmlContent(
            "Min side søknad test producer", {
                a(
                    href = "/",
                    classes = "navds-link navds-link--action tilbake-til-alle-soknader"
                ) {
                    +"Tilbake til alle søknad"
                }
                h1(classes = "navds-heading navds-heading--medium") { +"Event opprett sokand er sendt" }

            }, Environment.navDecoratorenUrl
        )
    }
}