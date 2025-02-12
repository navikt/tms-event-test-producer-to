package no.nav.tms.event.test.producer.to.kvittering

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import no.nav.tms.event.test.producer.to.tokenexchange.TokenFetcher

suspend fun fetchKvitteringer(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient,
    tokenXUser: String
) : List<ApiDto.SoknadsKvittering>{
    val soknadskvitteringToken = tokenFetcher.soknadskvitteringToken(tokenXUser)

    return httpClient.get("$soknadskvitteringUrl/kvitteringer/alle") {
        header("Authorization", "Bearer $soknadskvitteringToken")
    }.let { response ->
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Feil ved henting av kvitteringer: ${response.status}")
        } else {
            response.body()
        }
    }

}suspend fun fetchEnkelSoknad(
    tokenFetcher: TokenFetcher,
    soknadskvitteringUrl: String,
    httpClient: HttpClient,
    tokenXUser: String,
    soknadsId: String
) : ApiDto.SoknadsKvittering{
    val soknadskvitteringToken = tokenFetcher.soknadskvitteringToken(tokenXUser)

    return httpClient.get("$soknadskvitteringUrl/kvittering/$soknadsId") {
        header("Authorization", "Bearer $soknadskvitteringToken")
    }.let { response ->
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Feil ved henting av enkel soknad id: $soknadsId  and  status: ${response.status}")
        } else {
            response.body()
        }
    }

}