object Versions {
}

object Dependencies {
  const val stdLib = "stdlib-jdk8"
}

val implementation by configurations
dependencies {
  implementation(kotlin(Dependencies.stdLib))
}
