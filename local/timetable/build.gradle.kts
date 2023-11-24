@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("suwiki.android.library")
  id("suwiki.android.hilt")
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.suwiki.local.timetable"
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.data.timetable)
  implementation(projects.core.database)

  ksp(libs.room.compiler)
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)

  testImplementation(libs.junit4)
  androidTestImplementation(libs.junit)
}
