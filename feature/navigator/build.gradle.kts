@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.feature.compose)
}

android {
  namespace = "com.suwiki.feature.navigator"
}

dependencies {
  implementation(projects.feature.lectureevaluation.editor)
  implementation(projects.feature.lectureevaluation.my)
  implementation(projects.feature.lectureevaluation.viewerreporter)

  implementation(projects.feature.login)
  implementation(projects.feature.myinfo)
  implementation(projects.feature.notice)
  implementation(projects.feature.openmajor)
  implementation(projects.feature.signup)
  implementation(projects.feature.timetable)
}
