package no.nav.tms.event.test.producer.to.gui

import io.ktor.server.routing.*
import kotlinx.html.*


fun Route.startPage() {
    get{
        call.respondHtmlContent(
            "Min side søknad test producer",
        ){
            h1 { +"Opprett soknadskvittering"}
            p {+ "Her kan du opprette og redigere søknadseventer for testing på Min side. " }
            h2 {+ "Liste med soknadskvitteringer"}
        }
    }
}