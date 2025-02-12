package no.nav.tms.event.test.producer.to.kvittering

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher

suspend fun fetchKvitteringer(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient,
    tokenXUser: String
) {
    val soknadskvitteringToken = tokenFetcher.soknadskvitteringToken(tokenXUser)

    return httpClient.get("$soknadskvitteringUrl/kvitteringer/alle") {
        header("Authorization", "Bearer $soknadskvitteringToken")
    }.let { response ->
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Feil ved henting av kvitteringer: ${response.status}")
        } else {
            response.bodyAsText()
        }
    }

}