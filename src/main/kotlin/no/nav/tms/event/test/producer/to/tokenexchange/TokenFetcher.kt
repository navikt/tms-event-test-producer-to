package no.nav.tms.event.test.producer.to.tokenexchange

import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import no.nav.tms.token.support.tokenx.validation.user.TokenXUser

class TokenFetcher(
    private val tokendingsService: TokendingsService,
    private val soknadskvitteringClientId: String
) {
    suspend fun soknadskvitteringToken(user: TokenXUser) {
        try {
            tokendingsService.exchangeToken(token = user.tokenString, targetApp = soknadskvitteringClientId)
        } catch (e: Exception) {
            throw Exception("henting av token for soknadskvittering med clientId $soknadskvitteringClientId feiler")
        }
    }
}
