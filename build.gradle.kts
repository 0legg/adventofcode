import io.gitlab.arturbosch.detekt.Detekt
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

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "14"
    allWarningsAsErrors = true
    freeCompilerArgs += listOf(
      "-opt-in=kotlin.RequiresOptIn",
      "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      "-opt-in=kotlinx.coroutines.FlowPreview",
    )
  }
}

tasks.withType<Detekt>().configureEach {
  jvmTarget = "14"
}

detekt {
  config = files("detekt.yml")
}

dependencies {
  implementation(Kotlin.stdlib.jdk8)
  implementation("org.jetbrains.kotlin:kotlin-reflect:_")
  implementation(KotlinX.coroutines.core)
  implementation(KotlinX.serialization.json)

  implementation(Ktor.client.cio)

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}
