object Versions {
//  const val commonsCodec = "1.12"
//  const val commonsDbcp2 = "2.6.0"
//  const val commonsLang3 = "3.9"
//  const val commonsPool2 = "2.6.2"
//  const val beanutils = "1.9.3"
//  const val commonConfiguration = "1.10"
//  const val lombok = "1.18.8"
//  const val modelMapper = "2.3.5"
//  const val guava = "28.0-jre"
//  const val jackson = "2.9.9"
//  const val javaassist = "3.25.0-GA"
//  const val jaxb = "2.3.0"
//  const val activation = "1.1"
  const val commonsCodec = "1.15"
  const val commonsDbcp2 = "2.8.0"
  const val commonsLang3 = "3.11"
  const val commonsPool2 = "2.9.0"
  const val beanutils= "1.9.4"
  const val commonConfiguration= "1.10"
  const val lombok= "1.18.16"
  const val modelMapper= "2.3.9"
  const val guava= "30.1-jre"
  const val jackson= "2.11.3"
  const val javaassist= "3.27.0-GA"
  const val activation = "1.1.1"
  const val javax = "4.0.1"
}

object Dependencies {
  const val commonsCodec = "commons-codec:commons-codec:${Versions.commonsCodec}"
  const val commonsDbcp2 = "org.apache.commons:commons-dbcp2:${Versions.commonsDbcp2}"
  const val commonsLang3 = "org.apache.commons:commons-lang3:${Versions.commonsLang3}"
  const val commonsPool2 = "org.apache.commons:commons-pool2:${Versions.commonsPool2}"
  const val beanutils = "commons-beanutils:commons-beanutils:${Versions.beanutils}"
  const val commonConfiguration = "commons-configuration:commons-configuration:${Versions.commonConfiguration}"
  const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
  const val modelMapper = "org.modelmapper:modelmapper:${Versions.modelMapper}"
  const val guava = "com.google.guava:guava:${Versions.guava}"
  const val jackson = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
  const val javaassist = "org.javassist:javassist:${Versions.javaassist}"
  const val activation = "javax.activation:activation:${Versions.activation}"
  const val javaxServlet = "javax.servlet:javax.servlet-api:${Versions.javax}"
}


val implementation by configurations
val annotationProcessor by configurations
dependencies {
  implementation(Dependencies.commonsCodec)
  implementation(Dependencies.commonsDbcp2)
  implementation(Dependencies.commonsLang3)
  implementation(Dependencies.commonsPool2)
  implementation(Dependencies.beanutils)
  implementation(Dependencies.commonConfiguration)
  implementation(Dependencies.lombok)
  implementation(Dependencies.modelMapper)
  implementation(Dependencies.guava)
  implementation(Dependencies.jackson)
  implementation(Dependencies.javaassist)
  implementation(Dependencies.activation)
  implementation(Dependencies.javaxServlet)
  annotationProcessor(Dependencies.lombok)
}

