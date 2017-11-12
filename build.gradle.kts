plugins {
    kotlin("jvm") version "1.1.51"
}

repositories {
    jcenter()
}

object Libs {
    const val retrofit = "2.3.0"
    const val klaxon =  "0.32"
}

dependencies {
    compile(kotlin("stdlib-jre8"))
    compile(group = "com.squareup.retrofit2", name = "retrofit", version = Libs.retrofit)
    compile(group = "com.squareup.retrofit2", name = "converter-scalars", version = Libs.retrofit)
    compile(group = "com.beust", name = "klaxon", version = Libs.klaxon)
}

group = "net.olegg.adventofcode"
version = "3.0.0"
