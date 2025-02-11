package no.nav.tms.event.test.producer.to.gui

import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.html.*


suspend fun ApplicationCall.respondHtmlContent(
    title: String,
    builder: MAIN.() -> Unit,
) {

    this.respondHtml {
        head {
            lang = "nb"
            title(title)
            link {
                rel = "stylesheet"
                href = "/static/style.css"
            }
            link {
                rel = "stylesheet"
                href = "https://dekoratoren.ekstern.dev.nav.no/css/client.css"
            }

            script {
                src="https://dekoratoren.ekstern.dev.nav.no/client.js"
            }
        }
        body {
            //TODO fix heading med logo og logg-ut knapp
            div {
                id = "decorator-header"
            }
            main {
                builder()
            }
            div {
                id = "decorator-footer"
            }
            div {
                id = "decorator-env"
                attributes["data-src"] = "https://dekoratoren.ekstern.dev.nav.no/env?simpleHeader=true&simpleFooter=true&shareScreen=false&chatbot=false"
            }
        }
    }
}