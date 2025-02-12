package no.nav.tms.event.test.producer.to

import io.ktor.client.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.tms.common.util.config.IntEnvVar.getEnvVarAsInt
import no.nav.tms.event.test.producer.to.gui.gui
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder.buildTokendingsService


fun main() {
    val environment = Environment()
    val httpClient = HttpClient { configureClient() }

    embeddedServer(
        Netty,
        port = getEnvVarAsInt("PORT", 8081),
        module = {
            gui(
                environment.navDecoratorenUrl,
                tokenFetcher = TokenFetcher(
                    buildTokendingsService(),
                    environment.soknadskvitteringClientId,
                ),
                soknadskvitteringUrl="http://tms-soknadskvittering/",
                httpClient,
            )
        }
    ).start(wait = true)
}

