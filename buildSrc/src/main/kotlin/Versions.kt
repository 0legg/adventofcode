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

    const val org_jmailen_kotlinter_gradle_plugin: String = "1.20.1" 

    const val org_spekframework_spek2: String = "2.0.0-rc.1" 
            /* Could not find any version that matches org.spekframework.spek2:spek-dsl-jvm:+.
            Versions rejected by component selection rules: 2.0.0-rc.1
            Searched in the following locations: https://jcenter.bintray.com/org/spekframework/spek2/spek-dsl-jvm/maven-metadata.xml 
            .... */

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.0-rc-4"

        const val currentVersion: String = "5.0"

        const val nightlyVersion: String = "5.1-20181201000046+0000"

        const val releaseCandidate: String = ""
    }
}
