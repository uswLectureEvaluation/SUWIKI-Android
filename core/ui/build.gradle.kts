@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.library.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.kotlin.parcelize)
}

android {
  namespace = "com.suwiki.core.ui"
}
dependencies {
  implementation(projects.core.model)
  implementation(libs.kotlinx.immutable)
  implementation(libs.kotlinx.serialization.json)
}
