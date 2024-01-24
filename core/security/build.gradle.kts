@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.suwiki.core.security"
}

dependencies {
  implementation(projects.core.model)

  implementation(libs.bundles.coroutine)

  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)

  ksp(libs.encrypted.datastore.preference.ksp)
  implementation(libs.encrypted.datastore.preference.ksp.annotations)
  implementation(libs.encrypted.datastore.preference.security)

  implementation(libs.timber)

  testImplementation(libs.junit)
  androidTestImplementation(libs.junit)
}
