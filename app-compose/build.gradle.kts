@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.suwiki.android.application)
  alias(libs.plugins.suwiki.android.application.compose)
  alias(libs.plugins.suwiki.android.hilt)
}

android {
  namespace = "com.kunize.uswtimetable"

  defaultConfig {
    applicationId = "com.kunize.uswtimetable"
    versionCode = 31
    versionName = "2.2.4"
  }
}

dependencies {
  implementation(projects.feature.navigator)

  implementation(libs.timber)
}
