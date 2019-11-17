import kotlin.String

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Update this file with
 *   `$ ./gradlew buildSrcVersions`
 */
object Libs {
    /**
     * https://arturbosch.github.io/detekt
     */
    const val detekt_formatting: String = "io.gitlab.arturbosch.detekt:detekt-formatting:" +
            Versions.io_gitlab_arturbosch_detekt

    const val io_gitlab_arturbosch_detekt_gradle_plugin: String =
            "io.gitlab.arturbosch.detekt:io.gitlab.arturbosch.detekt.gradle.plugin:" +
            Versions.io_gitlab_arturbosch_detekt

    /**
     * https://github.com/spekframework/spek
     */
    const val spek_dsl_jvm: String = "org.spekframework.spek2:spek-dsl-jvm:" +
            Versions.org_spekframework_spek2

    /**
     * https://github.com/spekframework/spek
     */
    const val spek_runner_junit5: String = "org.spekframework.spek2:spek-runner-junit5:" +
            Versions.org_spekframework_spek2

    /**
     * https://github.com/square/retrofit/
     */
    const val converter_scalars: String = "com.squareup.retrofit2:converter-scalars:" +
            Versions.com_squareup_retrofit2

    /**
     * https://github.com/square/retrofit/
     */
    const val retrofit: String = "com.squareup.retrofit2:retrofit:" +
            Versions.com_squareup_retrofit2

    /**
     * https://kotlinlang.org/
     */
    const val kotlin_reflect: String = "org.jetbrains.kotlin:kotlin-reflect:" +
            Versions.org_jetbrains_kotlin

    /**
     * https://kotlinlang.org/
     */
    const val kotlin_scripting_compiler_embeddable: String =
            "org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:" +
            Versions.org_jetbrains_kotlin

    /**
     * https://kotlinlang.org/
     */
    const val kotlin_stdlib_jdk8: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" +
            Versions.org_jetbrains_kotlin

    /**
     * https://kotlinlang.org/
     */
    const val kotlin_test: String = "org.jetbrains.kotlin:kotlin-test:" +
            Versions.org_jetbrains_kotlin

    const val de_fayard_buildsrcversions_gradle_plugin: String =
            "de.fayard.buildSrcVersions:de.fayard.buildSrcVersions.gradle.plugin:" +
            Versions.de_fayard_buildsrcversions_gradle_plugin

    const val de_fuerstenau_buildconfig_gradle_plugin: String =
            "de.fuerstenau.buildconfig:de.fuerstenau.buildconfig.gradle.plugin:" +
            Versions.de_fuerstenau_buildconfig_gradle_plugin

    const val org_jetbrains_kotlin_jvm_gradle_plugin: String =
            "org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:" +
            Versions.org_jetbrains_kotlin_jvm_gradle_plugin

    /**
     * https://github.com/MarioAriasC/funKTionale
     */
    const val funktionale_memoization: String = "org.funktionale:funktionale-memoization:" +
            Versions.funktionale_memoization

    /**
     * https://github.com/cbeust/klaxon
     */
    const val klaxon: String = "com.beust:klaxon:" + Versions.klaxon
}
