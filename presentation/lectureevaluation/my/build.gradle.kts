@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.presentation.compose)
}

android {
  namespace = "com.suwiki.presentation.lectureevaluation.my"
}

dependencies {
  implementation(projects.domain.lectureevaluation.my)
  implementation(projects.domain.lectureevaluation.editor)
  implementation(projects.domain.user)

  implementation(libs.kotlinx.serialization.json)
}
