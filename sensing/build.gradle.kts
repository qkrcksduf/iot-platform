import org.springframework.boot.gradle.tasks.bundling.BootJar

val springBootVersion: String by project

val implementation by configurations

dependencies {
  implementation ("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}")
}
apply {
  from("../gradle/module/main/all-deps.gradle.kts")
  from("../gradle/module/main/spring/all-deps.gradle.kts")

  from("../gradle/module/test/all-deps.gradle.kts")
  from("../gradle/module/test/spring/all-deps.gradle.kts")
}