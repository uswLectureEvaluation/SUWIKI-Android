@file:Suppress("DSL_SCOPE_VIOLATION") // @file:을 붙인 이유 -> https://github.com/gradle/gradle/issues/20131

plugins {
  alias(libs.plugins.suwiki.java.library)
  alias(libs.plugins.kotlin.serialization)
}

dependencies {
  compileOnly(libs.compose.stable.marker)
  implementation(libs.kotlinx.serialization.json)
}
