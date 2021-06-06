val springBootVersion: String by project

val implementation by configurations
dependencies {
  implementation ("org.springframework.boot:spring-boot-starter:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-starter-actuator:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-starter-aop:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-starter-logging:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-starter-parent:${springBootVersion}")
  implementation ("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
}
