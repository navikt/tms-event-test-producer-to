plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm").version(Kotlin.version)

    id(TmsJarBundling.plugin)

    // Apply the application plugin to add support for building a CLI application.
    application
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(KotliQuery.kotliquery)
    implementation(KotlinLogging.logging)
    implementation(Logstash.logbackEncoder)
    implementation(Ktor.Server.core)
    implementation(Ktor.Server.netty)
    implementation(Ktor.Server.htmlDsl)
    implementation(Ktor.Server.statusPages)
    implementation(Ktor.Server.auth)
    implementation(Ktor.Server.authJwt)
    implementation(Ktor.Client.contentNegotiation)
    implementation(Ktor.Client.apache)
    implementation(Ktor.Client.core)
    implementation(Ktor.Serialization.jackson)
    implementation(TmsCommonLib.utils)
    implementation(TmsKtorTokenSupport.idportenSidecar)
    implementation(TmsKtorTokenSupport.tokendingsExchange)
    implementation(TmsSoknadskvittering.kotlinBuilder)
    implementation(Kafka.clients)
    testImplementation(TmsKtorTokenSupport.idportenSidecarMock)
}




repositories {
    mavenCentral()
    maven {
        url = uri("https://github-package-registry-mirror.gc.nav.no/cached/maven-release")
    }
    mavenLocal()
}

application {
    mainClass.set("no.nav.tms.event.test.producer.to.ApplicationKt")
}

configurations.all {
    resolutionStrategy {
        force("net.minidev:json-smart:2.5.1")
    }
}
