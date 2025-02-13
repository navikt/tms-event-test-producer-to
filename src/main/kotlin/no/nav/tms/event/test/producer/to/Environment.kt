package no.nav.tms.event.test.producer.to

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import no.nav.tms.common.util.config.StringEnvVar.getEnvVar
import java.text.DateFormat

object Environment {
    val navDecoratorenUrl: String = getEnvVar("NAV_DECORATOREN_URL")
    val soknadskvitteringClientId: String = getEnvVar("SOKNADSKVITTERING_CLIENT_ID")
}



fun HttpClientConfig<*>.configureClient() {
    install(ContentNegotiation) {
        jackson {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            registerModule(JavaTimeModule())
            dateFormat = DateFormat.getDateTimeInstance()
        }
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 3000
    }

}