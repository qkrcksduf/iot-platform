object Versions {
}

object Dependencies {
  const val kotlinTest = "org.jetbrains.kotlin:kotlin-test"
  const val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit"
}

val testImplementation by configurations
dependencies {
  testImplementation(Dependencies.kotlinTest)
  testImplementation(Dependencies.kotlinTestJUnit)
}
