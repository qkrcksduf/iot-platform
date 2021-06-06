val springDocOpenApiVersion: String by project
val springDocOpenApiImplVersion: String by project

val implementation by configurations

dependencies {
  implementation ("org.springdoc:springdoc-openapi:${springDocOpenApiVersion}")
  implementation ("org.springdoc:springdoc-openapi-webmvc-core:${springDocOpenApiVersion}")
  implementation ("org.springdoc:springdoc-openapi-ui:${springDocOpenApiVersion}")
}