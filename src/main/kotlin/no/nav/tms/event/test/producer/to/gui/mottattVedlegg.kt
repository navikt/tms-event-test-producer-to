package no.nav.tms.event.test.producer.to.gui

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.gui.components.descriptionItems
import no.nav.tms.event.test.producer.to.kvittering.SoknadEventProducer
import no.nav.tms.event.test.producer.to.kvittering.SoknadRequest
import no.nav.tms.soknad.event.validation.SoknadsKvitteringValidationException


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
        val mottattVedlegg = SoknadRequest.MottaVedlegg(
            soknadsId = soknadsId,
            vedleggsId = vedleggsId,
            brukerErAvsender = brukerErAvsender,
            tittel = tittel,
            linkVedlegg = linkVedlegg
        )
        try {
            soknadEventProducer.mottaVedlegg(mottattVedlegg)
            log.info { "Vedlegg-etterspurt event med id [${mottattVedlegg.soknadsId}] er lagt på kafka" }
        } catch (e: SoknadsKvitteringValidationException) {
            log.warn(e) { "Feilaktig innhold i motta-vedlegg request" }
            call.respond(HttpStatusCode.BadRequest)
        }
        call.respondHtmlContent(
            "Min side søknad test producer", {
                a(
                    href = "/soknad/${mottattVedlegg.soknadsId}",
                    classes = "navds-link navds-link--action tilbake-til-alle-soknader"
                ) {
                    +"Tilbake til soknad"
                }
                h1(classes = "navds-heading navds-heading--medium") { +"Event vedleggMottatt er sendt med følgende data:" }
                dl("navds-form-summary__answers") {
                    unsafe { +descriptionItems("soknadsId", mottattVedlegg.soknadsId) }
                    unsafe { +descriptionItems("vedleggsId", mottattVedlegg.vedleggsId) }
                    unsafe { +descriptionItems("brukerErAvsender", mottattVedlegg.brukerErAvsender.toString()) }
                    unsafe { +descriptionItems("tittel", mottattVedlegg.tittel) }
                    unsafe { +descriptionItems("linkVedlegg", mottattVedlegg.linkVedlegg) }
                }

            }, Environment.navDecoratorenUrl
        )
    }
}