@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("suwiki.android.library")
  id("suwiki.android.hilt")
  id("org.jetbrains.kotlin.plugin.serialization")
  id("com.google.devtools.ksp")
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

  testImplementation(libs.junit4)
  androidTestImplementation(libs.junit4)
}
