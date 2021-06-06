val springBootVersion: String by project

val implementation by configurations
val runtimeOnly by configurations

dependencies {
  implementation ("org.springframework.boot:spring-boot:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-autoconfigure:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-devtools:${springBootVersion}")
}