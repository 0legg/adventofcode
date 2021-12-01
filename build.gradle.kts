import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  id("com.github.gmazzo.buildconfig")
  id("io.gitlab.arturbosch.detekt")
}

group = "net.olegg.aoc"
version = "2021.0.0"

repositories {
  mavenCentral()
}

buildConfig {
  packageName(project.group.toString())
  buildConfigField("String", "COOKIE", "\"${project.findProperty("COOKIE")?.toString() ?: "Please provide cookie"}\"")
}

detekt {
  config = files("detekt.yml")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
    allWarningsAsErrors = true
    freeCompilerArgs += listOf(
      "-Xopt-in=kotlin.RequiresOptIn",
      "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      "-Xopt-in=kotlinx.coroutines.ObsoleteCoroutinesApi",
      "-Xopt-in=kotlinx.coroutines.FlowPreview",
    )
  }
}

dependencies {
  implementation(Kotlin.stdlib.jdk8)
  implementation("org.jetbrains.kotlin:kotlin-reflect:_")
  implementation(KotlinX.coroutines.core)
  implementation(KotlinX.serialization.json)

  implementation(Ktor.client.cio)

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}
