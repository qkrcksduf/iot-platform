val springBootVersion: String by project

val testImplementation by configurations
dependencies {
  testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
}
