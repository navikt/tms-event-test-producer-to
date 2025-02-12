package no.nav.tms.event.test.producer.to

import io.ktor.client.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.tms.common.util.config.IntEnvVar.getEnvVarAsInt
import no.nav.tms.event.test.producer.to.gui.gui
import no.nav.tms.event.test.producer.to.kvittering.Kafka
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.soknadskvittering.builder.SoknadEventBuilder
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder.buildTokendingsService
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.UUID


fun main() {
    val httpClient = HttpClient { configureClient() }
    val kafkaProducer = Kafka.initializeKafkaProducer()

    //val id = UUID.randomUUID().toString()
    //val skbuilder = SoknadEventBuilder.opprettet { soknadsId=id } //Eventet med
    //kafkaProducer.send(ProducerRecord("min-side.aapen-soknadskvittering-v1", id, skbuilder))

    embeddedServer(
        Netty,
        port = getEnvVarAsInt("PORT", 8081),
        module = {
            gui(
                tokenFetcher = TokenFetcher(
                    buildTokendingsService(),
                    Environment.soknadskvitteringClientId,
                ),
                soknadskvitteringUrl="http://tms-soknadskvittering/",
                httpClient,
            )
        }
    ).start(wait = true)
}

