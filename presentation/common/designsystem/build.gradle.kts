@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.library.compose)
}

android {
  namespace = "com.suwiki.presentation.common.designsystem"
}

dependencies {
  implementation(projects.common.model)
  implementation(projects.presentation.common.ui)

  implementation(libs.kotlinx.immutable)
  implementation(libs.compose.rating.bar)
}
