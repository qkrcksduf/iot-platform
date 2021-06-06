object Versions {
    const val axonVersion = "4.4.3"
}

object Dependencies {
    const val axonSpringBootStarter = "org.axonframework:axon-spring-boot-starter:${Versions.axonVersion}"
    const val axaxonConfiguration = "org.axonframework:axon-configuration:${Versions.axonVersion}"
    const val typetools = "net.jodah:typetools:0.6.2"
}


val implementation by configurations

dependencies {
    implementation(Dependencies.axonSpringBootStarter)
    implementation(Dependencies.axaxonConfiguration)
    implementation(Dependencies.typetools)
}


