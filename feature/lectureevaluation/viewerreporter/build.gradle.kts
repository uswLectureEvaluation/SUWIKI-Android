@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.feature.compose)
}

android {
  namespace = "com.suwiki.feature.lectureevaluation.viewerreporter"
}

dependencies {
  implementation(projects.domain.lectureevaluation.viewerreporter)
}
