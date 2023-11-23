@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("suwiki.android.remote")
  id("org.jetbrains.kotlin.plugin.serialization")
}

android {
  namespace = "com.suwiki.remote.lectureevaluation.viewerreporter"
}

dependencies {
  implementation(projects.data.lectureevaluation.viewerreporter)

  implementation(libs.retrofit.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.datetime)
}
