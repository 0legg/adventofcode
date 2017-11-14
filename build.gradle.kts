import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }
}

plugins {
    kotlin("jvm") version "1.1.60"
    idea
    id("org.jmailen.kotlinter") version "1.5.0"
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
}

configure<JUnitPlatformExtension> {
    filters {
        engines {
            include("spek")
        }
    }
}

buildConfig {
    buildConfigField("String", "COOKIE", project.findProperty("COOKIE")?.toString() ?: "Please provide cookie")
}

dependencies {
    compile(kotlin("stdlib-jre8"))
    compile(kotlin("reflect"))
    compile(group = "com.squareup.retrofit2", name = "retrofit", version = Libs.retrofit)
    compile(group = "com.squareup.retrofit2", name = "converter-scalars", version = Libs.retrofit)
    compile(group = "com.beust", name = "klaxon", version = Libs.klaxon)

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
