@file:Suppress("DSL_SCOPE_VIOLATION") // @file:을 붙인 이유 -> https://github.com/gradle/gradle/issues/20131

plugins {
  alias(libs.plugins.suwiki.java.library)
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.core.common)

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.hilt.core)
}
