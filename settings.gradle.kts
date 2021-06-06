rootProject.name = "demo"

pluginManagement {
    val kotlinPluginVersion: String by settings

    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val lombokVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinPluginVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinPluginVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("io.freefair.lombok") version lombokVersion
    }
}

include(
    "account",
    "cnc",
    "device",
    "sensing",
    "actuating",
    "device",
    "api-gateway",
    "service-registry",
    "model",
    "common"
)



