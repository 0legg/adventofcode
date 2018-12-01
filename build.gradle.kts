import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version(Versions.org_jetbrains_kotlin_jvm_gradle_plugin)
    id("org.jmailen.kotlinter").version(Versions.org_jmailen_kotlinter_gradle_plugin)
    id("de.fuerstenau.buildconfig").version(Versions.de_fuerstenau_buildconfig_gradle_plugin)
    id("jmfayard.github.io.gradle-kotlin-dsl-libs").version(Versions.jmfayard_github_io_gradle_kotlin_dsl_libs_gradle_plugin)
}

group = "net.olegg.adventofcode"
version = "3.0.0"

repositories {
    jcenter()
}

buildConfig {
    buildConfigField("String", "COOKIE", project.findProperty("COOKIE")?.toString() ?: "Please provide cookie")
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

    testImplementation(Libs.kotlin_test)
    testImplementation(Libs.spek_dsl_jvm)
    testRuntimeOnly(Libs.spek_runner_junit5)
}
