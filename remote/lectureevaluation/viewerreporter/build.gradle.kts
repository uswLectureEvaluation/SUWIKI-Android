@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.remote)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.suwiki.remote.lectureevaluation.viewerreporter"
}

dependencies {
  implementation(projects.data.lectureevaluation.viewerreporter)
  implementation("me.jessyan:retrofit-url-manager:1.4.0")


  implementation(libs.retrofit.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.datetime)
}
