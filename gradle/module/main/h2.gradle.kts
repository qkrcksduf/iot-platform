object Versions {
  const val h2 = "1.4.200"
  const val hibernate = "5.4.27.Final"
  const val hibernateTypes = "2.10.1"
  const val pg = "42.2.18.jre7"

}

object Dependencies {
  const val h2 = "com.h2database:h2:${Versions.h2}"
  const val hibernate = "org.hibernate:hibernate-core:${Versions.hibernate}"
  const val hibernateTypes = "com.vladmihalcea:hibernate-types-52:${Versions.hibernateTypes}"
  const val pg = "org.postgresql:postgresql:${Versions.pg}"
}


val implementation by configurations
dependencies {
  implementation(Dependencies.h2)
  implementation(Dependencies.hibernate)
  implementation(Dependencies.hibernateTypes)
  implementation(Dependencies.pg)
}
