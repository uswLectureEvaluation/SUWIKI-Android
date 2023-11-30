@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.suwiki.local.openmajor"
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.data.openmajor)
  implementation(projects.core.database)

  ksp(libs.room.compiler)
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)

  implementation(libs.bundles.coroutine)
  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)

  testImplementation(libs.junit4)
  androidTestImplementation(libs.junit)
}
