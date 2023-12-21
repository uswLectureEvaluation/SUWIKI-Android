@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
}

android {
  namespace = "com.suwiki.core.android"
}

dependencies {
  implementation(projects.core.model)

  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.crashlytics)
}
