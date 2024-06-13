@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
}

android {
  namespace = "com.suwiki.local.user"
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.data.user)
  implementation(projects.core.datastore)

  implementation(libs.bundles.coroutine)
  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)
  implementation(libs.protobuf.kotlin.lite)

  testImplementation(libs.junit4)
  androidTestImplementation(libs.junit4)
}
