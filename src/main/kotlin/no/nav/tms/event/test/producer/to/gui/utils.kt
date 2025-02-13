package no.nav.tms.event.test.producer.to.gui

fun getStringOrNull(input: String?): String? {
    return if (input.isNullOrBlank()) {
        null
    } else input
}