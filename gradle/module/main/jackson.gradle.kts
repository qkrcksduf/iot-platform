object Versions {
  const val jacksonDataType = "2.11.4"
}

object Dependencies {
  const val jacksonDataType = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonDataType}"
}


val implementation by configurations
dependencies {
  implementation(Dependencies.jacksonDataType)
}
