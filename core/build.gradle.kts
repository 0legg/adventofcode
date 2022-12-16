plugins {
  kotlin("jvm")
  id("com.github.gmazzo.buildconfig")
}

buildConfig {
  packageName(project.group.toString())
  buildConfigField("String", "COOKIE", "\"${project.findProperty("COOKIE")?.toString() ?: "Please provide cookie"}\"")
}

dependencies {
  implementation(Kotlin.stdlib.jdk8)
  implementation(Ktor.client.cio)
}
