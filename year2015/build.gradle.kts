plugins {
  alias(libs.plugins.kotlin.serialization)
}

dependencies {
  api(projects.core)

  implementation(libs.kotlinx.serialization.json)
}
