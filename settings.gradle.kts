pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("de.fayard.refreshVersions") version "0.60.3"
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
)
