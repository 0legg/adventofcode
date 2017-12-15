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
}

apply {
    plugin("org.junit.platform.gradle.plugin")
}

repositories {
    jcenter()
    maven("http://dl.bintray.com/jetbrains/spek")
}

object Libs {
    const val retrofit = "2.3.0"
    const val klaxon =  "0.32"
    const val spek = "1.1.5"
    const val funktionale = "1.2"
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
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile(group = "com.squareup.retrofit2", name = "retrofit", version = Libs.retrofit)
    compile(group = "com.squareup.retrofit2", name = "converter-scalars", version = Libs.retrofit)
    compile(group = "com.beust", name = "klaxon", version = Libs.klaxon)
    compile(group = "org.funktionale", name = "funktionale-memoization", version = Libs.funktionale)

    testCompile(kotlin("test"))
    testCompile(group ="org.jetbrains.spek", name = "spek-api", version = Libs.spek)
    testRuntime(group ="org.jetbrains.spek", name = "spek-junit-platform-engine", version = Libs.spek)
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
