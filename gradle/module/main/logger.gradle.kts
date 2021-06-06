object Versions {
  const val logback = "1.2.3"
  const val slf4j = "1.7.30"
}

object Dependencies {
  const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"
  const val logbackCore = "ch.qos.logback:logback-core:${Versions.logback}"

  const val jclOverSlf4jApi = "org.slf4j:jcl-over-slf4j:${Versions.slf4j}"
  const val julToSlf4j = "org.slf4j:jul-to-slf4j:${Versions.slf4j}"
  const val log4jOverSlf4j = "org.slf4j:log4j-over-slf4j:${Versions.slf4j}"
  const val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
}

val implementation by configurations
dependencies {
  implementation(Dependencies.logbackClassic)
  implementation(Dependencies.logbackCore)

  implementation(Dependencies.jclOverSlf4jApi)
  implementation(Dependencies.julToSlf4j)
  implementation(Dependencies.log4jOverSlf4j)
  implementation(Dependencies.slf4jApi)
}
