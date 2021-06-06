//object Versions {
//  const val typesafeConfig = "1.4.0"
//}
//
//object Dependencies {
//  const val typesafeConfig = "com.typesafe:config:${Versions.typesafeConfig}"
//}
//
//
//val implementation by configurations
//dependencies {
//  implementation(Dependencies.typesafeConfig)
//}

object Versions {
  const val typesafeConfig = "1.4.1"
  const val jaxb = "2.3.1"
}

object Dependencies {
  const val typesafeConfig = "com.typesafe:config:${Versions.typesafeConfig}"
  const val jaxb = "javax.xml.bind:jaxb-api:${Versions.jaxb}"
  const val jaxbRuntime = "org.glassfish.jaxb:jaxb-runtime:${Versions.jaxb}"
}


val implementation by configurations
dependencies {
  implementation(Dependencies.typesafeConfig)
  implementation(Dependencies.jaxb)
  implementation(Dependencies.jaxbRuntime)
}
