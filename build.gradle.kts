import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    mavenCentral()
  }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  base
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.detekt)
}

allprojects {
  group = "net.olegg.aoc"
  version = "2022.0.0"

  repositories {
    mavenCentral()
  }

  apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
  apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

  dependencies {
    val implementation by configurations
    @Suppress("UnstableApiUsage")
    implementation(rootProject.libs.kotlinx.coroutines.core)
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
  detektPlugins(libs.detekt.formatting)
}
