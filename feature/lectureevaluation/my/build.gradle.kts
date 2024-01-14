@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.feature.compose)
}

android {
  namespace = "com.suwiki.feature.lectureevaluation.my"
}

dependencies {
  implementation(projects.domain.lectureevaluation.my)
  implementation(libs.kotlinx.serialization.json)
}
