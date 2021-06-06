object Versions {
  const val assertj = "3.16.1"
}

object Dependencies {
  const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
}

val testImplementation by configurations
dependencies {
  testImplementation(Dependencies.assertj)
}
