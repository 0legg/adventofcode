import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  idea
  id("de.fuerstenau.buildconfig")
  id("io.gitlab.arturbosch.detekt")
}

group = "net.olegg.aoc"
version = "2020.0.0"

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
    freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
  }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
  implementation("org.jetbrains.kotlin:kotlin-reflect:_")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")

  implementation("com.squareup.okhttp3:okhttp:_")
  implementation("com.squareup.okio:okio:_")
  implementation("com.squareup.retrofit2:retrofit:_")
  implementation("com.squareup.retrofit2:converter-scalars:_")

  implementation("org.funktionale:funktionale-memoization:_")

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")

  testImplementation("org.jetbrains.kotlin:kotlin-test:_")
  testImplementation("org.spekframework.spek2:spek-dsl-jvm:_")
  testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:_")
}
