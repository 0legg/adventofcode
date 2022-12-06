import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    mavenCentral()
  }
}

plugins {
  base
  kotlin("jvm") apply false
  id("io.gitlab.arturbosch.detekt")
}

allprojects {
  group = "net.olegg.aoc"
  version = "2022.0.0"

  repositories {
    mavenCentral()
  }

  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "io.gitlab.arturbosch.detekt")

  dependencies {
    val implementation by configurations
    implementation(KotlinX.coroutines.core)
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
    config = rootProject.files("detekt.yml")
    baseline = rootProject.file("detekt-baseline.xml")
  }
}

dependencies {
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}
