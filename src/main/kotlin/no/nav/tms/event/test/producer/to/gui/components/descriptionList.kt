package no.nav.tms.event.test.producer.to.gui.components

import kotlinx.html.dd
import kotlinx.html.div
import kotlinx.html.dt
import kotlinx.html.stream.createHTML

fun descriptionItems(label: String, value: String?) = createHTML().run {
    div("navds-form-summary__answer") {
        dt("navds-label") { +label }
        dd("navds-form-summary__value navds-body-long navds-body-long--medium") { +(value ?: "null") }
    }
}