@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.buildconfig)
}

buildConfig {
  packageName(rootProject.group.toString())
  buildConfigField(
    type = "String",
    name = "COOKIE",
    value = rootProject.findProperty("COOKIE")?.toString() ?: "error(\"Please provide cookie\")"
  )
}

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.ktor.client.cio)
}
