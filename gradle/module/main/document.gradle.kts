object Versions {
  const val springfox = "3.0.0"
}

object Dependencies {
  const val springfox = "io.springfox:springfox-core:${Versions.springfox}"
  const val swagger2 = "io.springfox:springfox-swagger2:${Versions.springfox}"
  const val swaggerUi = "io.springfox:springfox-swagger-ui:${Versions.springfox}"

}


val implementation by configurations
dependencies {
  implementation(Dependencies.springfox)
  implementation(Dependencies.swagger2)
  implementation(Dependencies.swaggerUi)
}
