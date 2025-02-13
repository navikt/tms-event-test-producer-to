package no.nav.tms.event.test.producer.to.gui.pages

import io.ktor.server.routing.*
import kotlinx.html.*
import no.nav.tms.event.test.producer.to.Environment
import no.nav.tms.event.test.producer.to.gui.respondHtmlContent


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
                form(classes = "opprett-soknad-form") {
                    action = "/soknad/opprettet"
                    method = FormMethod.get
                    encType = FormEncType.multipartFormData
                    fieldSet {
                        legend { +"Detaljer om søknad" }
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
                                htmlFor = "temakode"
                                +"Temakode"
                            }
                            input {
                                id = "temakode"
                                name = "temakode"
                                maxLength = "3"
                                type = InputType.text
                                required = true
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "skjemanummer"
                                +"Skjemanummer"
                            }
                            input {
                                id = "skjemanummer"
                                name = "skjemanummer"
                                type = InputType.text
                                required = true
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "linkSoknad"
                                +"Link til soknad (optional)"
                            }
                            input {
                                id = "linkSoknad"
                                name = "linkSoknad"
                                type = InputType.text
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "journalpostId"
                                +"JournalpostId (optional)"
                            }
                            input {
                                id = "journalpostId"
                                name = "journalpostId"
                                type = InputType.text
                            }
                        }
                        div("navds-input form-group") {
                            label {
                                htmlFor = "fristEttersending "
                                +"Frist ettersending"
                            }
                            input {
                                id = "fristEttersending"
                                name = "fristEttersending"
                                type = InputType.date
                                required = true
                            }
                        }
                        button {
                            type = ButtonType.submit
                            +"Opprett soknad"
                        }
                    }
                }

            }, Environment.navDecoratorenUrl
        )
    }
}