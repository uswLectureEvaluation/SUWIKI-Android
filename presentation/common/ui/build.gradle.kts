@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.library.compose)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.suwiki.presentation.common.ui"
}

dependencies {
  implementation(projects.common.model)
  implementation(libs.kotlinx.immutable)
  implementation(libs.kotlinx.serialization.json)
}
