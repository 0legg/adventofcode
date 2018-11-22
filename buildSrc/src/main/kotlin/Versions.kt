import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew syncLibs`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val klaxon: String = "4.0.2" 

    const val com_squareup_retrofit2: String = "2.5.0" 

    const val de_fuerstenau_buildconfig_gradle_plugin: String = "1.1.8" 

    const val jmfayard_github_io_gradle_kotlin_dsl_libs_gradle_plugin: String = "0.2.6" 

    const val funktionale_memoization: String = "1.2" 

    const val org_jetbrains_kotlin_jvm_gradle_plugin: String = "1.3.10" 

    const val org_jetbrains_kotlin: String = "1.3.10" 

    const val org_jetbrains_spek: String = "1.2.1" 

    const val org_jmailen_kotlinter_gradle_plugin: String = "1.20.1" 

    const val junit_platform_gradle_plugin: String = "1.2.0" 

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "4.4"

        const val currentVersion: String = "5.0"

        const val nightlyVersion: String = "5.1-20181201000046+0000"

        const val releaseCandidate: String = ""
    }
}
