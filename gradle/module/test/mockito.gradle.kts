object Versions {
  const val mockito = "3.3.3"
}

object Dependencies {
  const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
  const val mockitoJunitJupiter = "org.mockito:mockito-junit-jupiter:${Versions.mockito}"
}

val testImplementation by configurations
dependencies {
  testImplementation(Dependencies.mockitoCore)
  testImplementation(Dependencies.mockitoJunitJupiter)
}
