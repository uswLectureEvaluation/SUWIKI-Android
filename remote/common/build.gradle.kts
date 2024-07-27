@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.library)
  alias(libs.plugins.suwiki.android.hilt)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.suwiki.remote.common"

  buildTypes {
    getByName("debug") {
      buildConfigField("String", "BASE_URL", "String.valueOf(\"http://54.180.72.97:8080\")")
    }

    getByName("release") {
      buildConfigField("String", "BASE_URL", "String.valueOf(\"https://api.suwiki.kr\")")
    }
  }
}

dependencies {
  implementation(projects.common.model)
  implementation(projects.common.security)

  implementation(libs.bundles.coroutine)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.kotlin.serialization)
  implementation(libs.okhttp.logging)
  androidTestImplementation(libs.junit4)

  implementation(libs.timber)
}
