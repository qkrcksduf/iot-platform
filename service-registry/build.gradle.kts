import org.springframework.boot.gradle.tasks.bundling.BootJar


apply {
  from("../gradle/module/main/all-deps.gradle.kts")
  from("../gradle/module/main/spring/all-deps.gradle.kts")

  from("../gradle/module/test/all-deps.gradle.kts")
  from("../gradle/module/test/spring/all-deps.gradle.kts")
}