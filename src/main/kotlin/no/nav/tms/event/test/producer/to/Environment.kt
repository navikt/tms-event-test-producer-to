package no.nav.tms.event.test.producer.to

import no.nav.tms.common.util.config.StringEnvVar.getEnvVar

data class Environment(
    val navDecoratorenUrl: String = getEnvVar("NAV_DECORATOREN_URL")
)
