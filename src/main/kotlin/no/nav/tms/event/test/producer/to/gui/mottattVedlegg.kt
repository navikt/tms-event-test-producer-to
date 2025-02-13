package no.nav.tms.event.test.producer.to.gui

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.kvittering.SoknadEventProducer
import no.nav.tms.event.test.producer.to.kvittering.SoknadRequest
import no.nav.tms.soknad.event.validation.SoknadsKvitteringValidationException
import org.apache.kafka.clients.producer.KafkaProducer


fun Route.mottattVedlegg(
    soknadEventProducer: SoknadEventProducer
) {
    get("soknad/vedlegg/motta") {
        val log = KotlinLogging.logger {}


        val soknadsId = call.request.queryParameters["soknadsId"] ?: throw IllegalArgumentException("Parameter 'soknadsId' is missing")
        val vedleggsId = call.request.queryParameters["vedleggsId"] ?: throw IllegalArgumentException("Parameter 'vedleggsId' is missing")
        val brukerErAvsender = call.request.queryParameters["brukerErAvsender"]?.toBoolean() ?: throw IllegalArgumentException("Parameter 'brukerErAvsender' is missing")
        val tittel = call.request.queryParameters["tittel"] ?: throw IllegalArgumentException("Parameter 'tittel' is missing")
        val linkVedlegg = call.request.queryParameters["linkVedlegg"]



            call.parameters["vedleggsId"] ?: throw IllegalArgumentException("Parameter 'vedleggId' is missing")
        val mottatt = SoknadRequest.MottaVedlegg(
            soknadsId = soknadsId,
            vedleggsId = vedleggsId,
            brukerErAvsender = brukerErAvsender,
            tittel = tittel,
            linkVedlegg = linkVedlegg
        )
        try {
            val mottaRequest: SoknadRequest.MottaVedlegg = call.receive()
            soknadEventProducer.mottaVedlegg(mottaRequest)
            log.info { "Vedlegg-etterspurt event med id [${mottaRequest.soknadsId}] er lagt på kafka" }
        } catch (e: SoknadsKvitteringValidationException) {
            log.warn(e) { "Feilaktig innhold i motta-vedlegg request" }
            call.respond(HttpStatusCode.BadRequest)
        }
        call.respondHtmlContent(
            "Min side søknad test producer", {
                a(
                    href = "/",
                    classes = "navds-link navds-link--action tilbake-til-alle-soknader"
                ) {
                    +"Tilbake til soknad"
                }
                h1(classes = "--a-font-size-heading-medium") { +"vedleggMottatt event har blitt sendt til tms-soknadskvittering" }
            }, Environment.navDecoratorenUrl
        )
    }
}