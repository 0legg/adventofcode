import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  id("com.github.gmazzo.buildconfig")
  id("io.gitlab.arturbosch.detekt")
}

group = "net.olegg.aoc"
version = "2022.0.0"

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
    languageVersion = "1.8"
    allWarningsAsErrors = true
    freeCompilerArgs += listOf(
      "-Xsuppress-version-warnings",
      "-opt-in=kotlin.RequiresOptIn",
      "-opt-in=kotlin.ExperimentalStdlibApi",
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
  baseline = file("detekt-baseline.xml")
}

dependencies {
  implementation(Kotlin.stdlib.jdk8)
  implementation("org.jetbrains.kotlin:kotlin-reflect:_")
  implementation(KotlinX.coroutines.core)
  implementation(KotlinX.serialization.json)

  implementation(Ktor.client.cio)

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}
