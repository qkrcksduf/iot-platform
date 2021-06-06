object Versions {
  const val zuul = "2.1.1.RELEASE"
  const val cloudParent = "2.1.5.RELEASE"
  const val cloud = "Greenwich.RELEASE"
  const val hystrix = "1.4.7.RELEASE"
}

object Dependencies {
  const val zuul = "org.springframework.cloud:spring-cloud-starter-netflix-zuul:${Versions.zuul}"
  const val hystrix = "org.springframework.cloud:spring-cloud-starter-hystrix:${Versions.hystrix}"
  const val Nhystrix = "org.springframework.cloud:spring-cloud-starter-netflix-hystrix:${Versions.zuul}"
  const val eureka = "org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:${Versions.zuul}"
  const val ribbon = "org.springframework.cloud:spring-cloud-starter-netflix-ribbon:${Versions.zuul}"
  const val cloudParent = "org.springframework.cloud:spring-cloud-dependencies-parent:${Versions.cloudParent}"
  const val cloud = "org.springframework.cloud:spring-cloud-dependencies:${Versions.cloud}"

}


val implementation by configurations
val runtimeOnly by configurations

dependencies {
  implementation(Dependencies.zuul)
  implementation(Dependencies.hystrix)
  implementation(Dependencies.Nhystrix)
  implementation(Dependencies.eureka)
  implementation(Dependencies.ribbon)
  implementation(Dependencies.cloudParent)
  implementation(Dependencies.cloud)

}


apply {
  from("../gradle/module/main/all-deps.gradle.kts")
  from("../gradle/module/main/spring/all-deps.gradle.kts")

  from("../gradle/module/test/all-deps.gradle.kts")
  from("../gradle/module/test/spring/all-deps.gradle.kts")
}