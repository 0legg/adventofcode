@file:Suppress("UnstableApiUsage")

import de.fayard.refreshVersions.core.StabilityLevel

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("de.fayard.refreshVersions") version "0.60.6"
}

refreshVersions {
  rejectVersionIf {
    candidate.stabilityLevel != StabilityLevel.Stable
  }
}

rootProject.name = "advent-of-code"

include(
  ":core",
  ":year2015",
  ":year2016",
  ":year2017",
  ":year2018",
  ":year2019",
  ":year2020",
  ":year2021",
  ":year2022",
  ":year2023",
  ":year2024",
  ":year2025",
)
