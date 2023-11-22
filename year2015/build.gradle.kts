plugins {
  alias(libs.plugins.kotlin.serialization)
}

dependencies {
  api(project(":core"))

  implementation(libs.kotlinx.serialization.json)
}
