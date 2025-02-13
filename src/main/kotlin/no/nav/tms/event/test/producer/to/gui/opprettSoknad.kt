package no.nav.tms.event.test.producer.to.gui

import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment


fun Route.opprettSoknad() {
    get("soknad/opprett") {
        call.respondHtmlContent(
            "Min side søknad test producer", {
                a(
                    href = "/",
                    classes = "navds-link navds-link--action tilbake-til-alle-soknader"
                ) {
                    +"Tilbake til alle søknader"
                }
                h1(classes = "navds-heading navds-heading--medium") { +"Opprett ny soknad" }
                form {
                    action = "/soknad/opprettet"
                    method = FormMethod.get
                    encType = FormEncType.multipartFormData
                    fieldSet {
                        legend { +"Detaljer om søknad" }
                        div("navds-input") {
                            label {
                                htmlFor = "tittel"
                                +"Tittel"
                            }
                            input {
                                id = "tittel"
                                name = "tittel"
                                type = InputType.text
                            }
                        }
                        div("navds-input") {
                            label {
                                htmlFor = "temakode"
                                +"Temakode"
                            }
                            input {
                                id = "temakode"
                                name = "temakode"
                                type = InputType.text
                            }
                        }
                        div("navds-input") {
                            label {
                                htmlFor = "linkSoknad"
                                +"Link til soknad"
                            }
                            input {
                                id = "linkSoknad"
                                name = "linkSoknad"
                                type = InputType.text
                            }
                        }
                        div("navds-input") {
                            label {
                                htmlFor = "skjemanummer"
                                +"Skjemanummer"
                            }
                            input {
                                id = "skjemanummer"
                                name = "skjemanummer"
                                type = InputType.text
                            }
                        }
                        div("navds-input") {
                            label {
                                htmlFor = "journalpostId"
                                +"JournalpostId"
                            }
                            input {
                                id = "journalpostId"
                                name = "journalpostId"
                                type = InputType.text
                            }
                        }
                        div("navds-input") {
                            label {
                                htmlFor = "fristEttersending"
                                +"Frist ettersending"
                            }
                            input {
                                id = "fristEttersending"
                                name = "fristEttersending"
                                type = InputType.dateTimeLocal
                            }
                        }
                        button {
                            type = ButtonType.submit
                        }
                    }
                }

            }, Environment.navDecoratorenUrl
        )
    }
}