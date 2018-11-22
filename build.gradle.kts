import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }
}

plugins {
    kotlin("jvm") version "1.2.10"
    idea
    id("org.jmailen.kotlinter") version "1.6.0"
    id("de.fuerstenau.buildconfig") version "1.1.8"
    id("jmfayard.github.io.gradle-kotlin-dsl-libs") version "0.2.6"
}

apply {
    plugin("org.junit.platform.gradle.plugin")
}

repositories {
    jcenter()
    maven("http://dl.bintray.com/jetbrains/spek")
}

configure<JUnitPlatformExtension> {
    filters {
        engines {
            include("spek")
        }
    }
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
    allWarningsAsErrors = true
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
    allWarningsAsErrors = true
}

buildConfig {
    buildConfigField("String", "COOKIE", project.findProperty("COOKIE")?.toString() ?: "Please provide cookie")
}

dependencies {
    compile(Libs.kotlin_stdlib_jdk8)
    compile(Libs.kotlin_reflect)
    compile(Libs.retrofit)
    compile(Libs.converter_scalars)
    compile(Libs.klaxon)
    compile(Libs.funktionale_memoization)

    testCompile(Libs.kotlin_test)
    testCompile(Libs.spek_api)
    testRuntime(Libs.spek_junit_platform_engine)
}

// extension for configuration
fun JUnitPlatformExtension.filters(setup: FiltersExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(FiltersExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}
fun FiltersExtension.engines(setup: EnginesExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}

group = "net.olegg.adventofcode"
version = "3.0.0"
