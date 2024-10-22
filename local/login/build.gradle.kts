@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
}

android {
  namespace = "com.suwiki.local.login"
}

dependencies {
  implementation(projects.common.model)
  implementation(projects.data.login)
  implementation(projects.common.security)

  implementation(libs.bundles.coroutine)

  testImplementation(libs.junit4)
  androidTestImplementation(libs.junit4)
}
