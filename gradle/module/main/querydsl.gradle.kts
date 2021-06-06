object Versions {
  const val querydsl = "4.4.0"

}

object Dependencies {
  const val querydslCore = "com.querydsl:querydsl-core:${Versions.querydsl}"
  const val querydslApt = "com.querydsl:querydsl-apt:${Versions.querydsl}"
  const val querydslJpa = "com.querydsl:querydsl-jpa:${Versions.querydsl}"

}

val implementation by configurations
dependencies {
  implementation(Dependencies.querydslCore)
  implementation(Dependencies.querydslApt)
  implementation(Dependencies.querydslJpa)

}
