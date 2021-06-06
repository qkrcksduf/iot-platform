object Versions {
  const val eureka = "2.2.6.RELEASE"
  const val cloud = "2.3.1.RELEASE"
}

object Dependencies {
  const val eureka = "org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:${Versions.eureka}"
  const val cloud = "org.springframework.cloud:spring-cloud-dependencies-parent:${Versions.cloud}"

}


val implementation by configurations
dependencies {
  implementation(Dependencies.eureka)
  implementation(Dependencies.cloud)
}
