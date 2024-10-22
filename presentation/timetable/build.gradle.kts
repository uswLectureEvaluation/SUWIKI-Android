@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.presentation.compose)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.suwiki.presentation.timetable"
}

dependencies {
  implementation(projects.domain.timetable)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.bundles.glance)
  implementation(libs.compose.toolbar)
}
