@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.feature.compose)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.suwiki.feature.timetable"
}

dependencies {
  implementation(projects.domain.timetable)
  implementation(libs.kotlinx.serialization.json)
}
