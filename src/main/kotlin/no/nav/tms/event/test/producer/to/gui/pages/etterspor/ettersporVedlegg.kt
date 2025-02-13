package no.nav.tms.event.test.producer.to.gui.pages.etterspor

import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.gui.respondHtmlContent


fun Route.ettersporVedlegg() {
    get("soknad/etterspor") {
        call.respondHtmlContent(
            "Min side søknad test producer", {
                a(
                    href = "/",
                    classes = "navds-link navds-link--action tilbake-til-alle-soknader"
                ) {
                    +"Tilbake til alle søknader"
                }
                h1(classes = "navds-heading navds-heading--medium") { +"Etterspør vedlegg til søknad" }
                form{
                    action = "/soknad/etterspurt"
                    method = FormMethod.get
                    encType = FormEncType.multipartFormData
                    fieldSet {
                        legend { +"Detaljer om vedlegg" }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "soknadsId"
                                +"SøknadsId"
                            }
                            input {
                                id = "soknadsId"
                                name = "soknadsId"
                                type = InputType.text
                                required = true
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "vedleggsId"
                                +"VedleggsId"
                            }
                            input {
                                id = "vedleggsId"
                                name = "vedleggsId"
                                type = InputType.text
                                required = true
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "brukerErAvsender"
                                +"Bruker er avsender"
                            }
                            input {
                                id = "brukerErAvsender"
                                name = "brukerErAvsender"
                                type = InputType.text
                                required = true
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "tittel"
                                +"Tittel"
                            }
                            input {
                                id = "tittel"
                                name = "tittel"
                                type = InputType.text
                                required = true
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "linkVedlegg"
                                +"Link til ettersending (optional)"
                            }
                            input {
                                id = "linkEttersending"
                                name = "linkEttersending"
                                type = InputType.text
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "beskrivelse"
                                +"Beskrivelse (optional)"
                            }
                            input {
                                id = "beskrivelse"
                                name = "beskrivelse"
                                type = InputType.text
                            }
                        }

                        button {
                            type = ButtonType.submit
                            +"Etterspør vedlegg"
                        }
                    }
                }


            }, Environment.navDecoratorenUrl
        )
    }
}