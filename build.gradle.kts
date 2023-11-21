import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    mavenCentral()
  }
}

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
    implementation(rootProject.libs.kotlinx.coroutines.core)
  }

  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      allWarningsAsErrors.set(true)
      freeCompilerArgs.set(
        listOf(
          "-Xsuppress-version-warnings",
          "-opt-in=kotlin.RequiresOptIn",
          "-opt-in=kotlin.ExperimentalStdlibApi",
          "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
          "-opt-in=kotlinx.coroutines.FlowPreview",
        )
      )
    }
  }

  extensions.configure<KotlinProjectExtension> {
    jvmToolchain(17)
  }

  detekt {
    config.from(rootProject.files("detekt.yml"))
    baseline = rootProject.file("detekt-baseline.xml")
  }
}

dependencies {
  detektPlugins(libs.detekt.formatting)
}
