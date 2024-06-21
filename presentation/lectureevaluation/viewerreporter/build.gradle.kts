@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.presentation.compose)
}

android {
  namespace = "com.suwiki.presentation.lectureevaluation.viewerreporter"
}

dependencies {
  implementation(projects.domain.user)
  implementation(projects.domain.lectureevaluation.viewerreporter)

  implementation(libs.compose.toolbar)
  implementation(libs.kotlinx.serialization.json)
}
