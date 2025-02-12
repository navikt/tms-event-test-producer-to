package no.nav.tms.event.test.producer.to.kvittering

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher
import no.nav.tms.token.support.tokenx.validation.user.TokenXUser

suspend fun fetchKvitteringer(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient,
    tokenXUser: TokenXUser
) {
    val soknadskvitteringToken = tokenFetcher.soknadskvitteringToken(tokenXUser)


    httpClient.get("$soknadskvitteringUrl/kvitteringer/alle") {
        header("Authorization", "Bearer $soknadskvitteringToken")
    }.bodyAsText()

}