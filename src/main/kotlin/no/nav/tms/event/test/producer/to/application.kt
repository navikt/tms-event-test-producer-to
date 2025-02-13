package no.nav.tms.event.test.producer.to

import io.ktor.client.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.tms.common.util.config.IntEnvVar.getEnvVarAsInt
import no.nav.tms.event.test.producer.to.gui.gui
import no.nav.tms.event.test.producer.to.kvittering.Kafka
import no.nav.tms.event.test.producer.to.kvittering.SoknadEventProducer
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder.buildTokendingsService


fun main() {
    val httpClient = HttpClient { configureClient() }
    val soknadEventProducer =
        SoknadEventProducer(Kafka.initializeKafkaProducer(), "min-side.aapen-soknadskvittering-v1")

    embeddedServer(
        Netty,
        port = getEnvVarAsInt("PORT", 8081),
        module = {
            gui(
                tokenFetcher = TokenFetcher(
                    buildTokendingsService(),
                    Environment.soknadskvitteringClientId,
                ),
                soknadskvitteringUrl = "http://tms-soknadskvittering/",
                httpClient,
                soknadEventProducer
            )
        }
    ).start(wait = true)
}

