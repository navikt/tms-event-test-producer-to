package no.nav.tms.event.test.producer.to.gui

fun getStringOrNull(input: String?): String? {
    return input?.isBlank()?.let { null } ?: input
}