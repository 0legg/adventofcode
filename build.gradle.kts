import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm").version(Versions.org_jetbrains_kotlin_jvm_gradle_plugin)
  idea
  id("de.fuerstenau.buildconfig").version(Versions.de_fuerstenau_buildconfig_gradle_plugin)
  buildSrcVersions
  id("io.gitlab.arturbosch.detekt").version(Versions.io_gitlab_arturbosch_detekt)
}

group = "net.olegg.adventofcode"
version = "3.0.0"

repositories {
  jcenter()
}

buildConfig {
  buildConfigField("String", "COOKIE", project.findProperty("COOKIE")?.toString() ?: "Please provide cookie")
}

detekt {
  config = files("detekt.yml")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Test> {
  useJUnitPlatform {
    includeEngines("spek2")
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
    allWarningsAsErrors = true
  }
}

dependencies {
  implementation(Libs.kotlin_stdlib_jdk8)
  implementation(Libs.kotlin_reflect)
  implementation(Libs.retrofit)
  implementation(Libs.converter_scalars)
  implementation(Libs.klaxon)
  implementation(Libs.funktionale_memoization)

  detektPlugins(Libs.detekt_formatting)

  testImplementation(Libs.kotlin_test)
  testImplementation(Libs.spek_dsl_jvm)
  testRuntimeOnly(Libs.spek_runner_junit5)
}
