import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  idea
  id("com.github.gmazzo.buildconfig")
  id("io.gitlab.arturbosch.detekt")
}

group = "net.olegg.aoc"
version = "2020.0.0"

repositories {
  mavenCentral()
  jcenter()
}

buildConfig {
  packageName(project.group.toString())
  buildConfigField("String", "COOKIE", "\"${project.findProperty("COOKIE")?.toString() ?: "Please provide cookie"}\"")
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
    freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
  }
}

dependencies {
  implementation(Kotlin.stdlib.jdk8)
  implementation("org.jetbrains.kotlin:kotlin-reflect:_")
  implementation(KotlinX.coroutines.core)
  implementation(KotlinX.serialization.json)

  implementation(Ktor.client.cio)

  implementation("org.funktionale:funktionale-memoization:_")

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")

  testImplementation(Kotlin.test)
  testImplementation(Testing.spek.dsl.jvm)
  testRuntimeOnly(Testing.spek.runner.junit5)
}
