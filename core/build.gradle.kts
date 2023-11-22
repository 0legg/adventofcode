plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.buildconfig)
}

buildConfig {
  packageName(rootProject.group.toString())
  useKotlinOutput()
  buildConfigField(
    type = "String",
    name = "COOKIE",
    value = rootProject.property("COOKIE").toString()
  )
}

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.ktor.client.cio)
}
