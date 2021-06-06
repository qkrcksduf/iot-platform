object Versions {
  const val gateway = "2.2.6.RELEASE"
}

object Dependencies {
  const val eureka = "org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:${Versions.gateway}"
  const val hystrix = "org.springframework.cloud:spring-cloud-starter-netflix-hystrix:${Versions.gateway}"
}


val implementation by configurations
dependencies {
  implementation(Dependencies.eureka)
  implementation(Dependencies.hystrix)
}
