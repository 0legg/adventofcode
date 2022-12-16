plugins {
  kotlin("plugin.serialization")
}

dependencies {
  api(project(":core"))

  implementation(KotlinX.serialization.json)
}
