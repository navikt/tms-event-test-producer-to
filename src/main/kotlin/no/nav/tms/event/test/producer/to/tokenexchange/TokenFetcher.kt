package no.nav.tms.event.test.producer.to.tokenexchange

import no.nav.tms.token.support.tokendings.exchange.TokendingsService

class TokenFetcher(
    private val tokendingsService: TokendingsService,
    private val soknadskvitteringClientId: String
) {
    suspend fun soknadskvitteringToken(token: String) : String{
        try {
            return tokendingsService.exchangeToken(token = token, targetApp = soknadskvitteringClientId)
        } catch (e: Exception) {
            throw Exception("henting av token for soknadskvittering med clientId $soknadskvitteringClientId feiler  ${e.cause}")
        }
    }
}
