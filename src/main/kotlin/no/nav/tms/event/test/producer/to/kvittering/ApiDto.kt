package no.nav.tms.event.test.producer.to.kvittering
import java.time.LocalDate
import java.time.ZonedDateTime

object ApiDto {
    data class SoknadsKvittering(
        val soknadsId: String,
        val tittel: String,
        val temakode: String,
        val skjemanummer: String,
        val tidspunktMottatt: ZonedDateTime,
        val fristEttersending: LocalDate,
        val linkSoknad: String?,
        val journalpostId: String?,
        val mottatteVedlegg: List<MottattVedlegg>,
        val manglendeVedlegg: List<ManglendeVedlegg>,
        val opprettet: ZonedDateTime
    )

    data class ManglendeVedlegg(
        val vedleggsId: String,
        val brukerErAvsender: Boolean,
        val tittel: String,
        val beskrivelse: String?,
        val linkEttersending: String?,
        val tidspunktEtterspurt: ZonedDateTime
    )

    data class MottattVedlegg(
        val vedleggsId: String,
        val brukerErAvsender: Boolean,
        val erEttersending: Boolean,
        val tittel: String,
        val linkVedlegg: String?,
        val tidspunktMottatt: ZonedDateTime
    )
}
