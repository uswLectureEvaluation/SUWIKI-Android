@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

ksp {
  arg("room.schemaLocation", "$rootDir/schemas")
}

android {
  namespace = "com.suwiki.core.database"

  sourceSets {
    // Adds exported schema location as test app assets.
    getByName("androidTest").assets.srcDir("$rootDir/schemas")
  }
}

dependencies {
  implementation(projects.core.model)

  ksp(libs.room.compiler)
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  androidTestImplementation(libs.room.testing)

  implementation(libs.bundles.coroutine)

  implementation(libs.kotlinx.serialization.json)

  androidTestImplementation(libs.androidx.junit.ktx)
  androidTestImplementation(libs.androidx.test.runner)
}
