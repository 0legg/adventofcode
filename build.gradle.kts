import org.jetbrains.kotlin.cli.jvm.main

plugins {
    kotlin("jvm") version "1.1.60"
    idea
    id("org.jmailen.kotlinter") version "1.5.0"
    id("de.fuerstenau.buildconfig") version "1.1.8"
}

repositories {
    jcenter()
}

object Libs {
    const val retrofit = "2.3.0"
    const val klaxon =  "0.32"
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
}

group = "net.olegg.adventofcode"
version = "3.0.0"
