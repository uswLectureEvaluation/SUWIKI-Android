@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.presentation.compose)
}

android {
  namespace = "com.suwiki.presentation.login"
}

dependencies {
  implementation(projects.domain.login)
  implementation(projects.domain.user)
}
