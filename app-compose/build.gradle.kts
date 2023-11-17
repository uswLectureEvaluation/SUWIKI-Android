plugins {
  id("suwiki.android.application")
  id("suwiki.android.application.compose")
  id("suwiki.android.hilt")
}

android {
  namespace = "com.kunize.uswtimetable"

  defaultConfig {
    applicationId = "com.kunize.uswtimetable"
    versionCode = 1
    versionName = "1.0"
  }
}

dependencies {
  implementation(projects.core.database)
  implementation(projects.core.model)
  implementation(projects.core.network)
  implementation(projects.core.security)

  implementation(projects.data)
  implementation(projects.data.openmajor)
  implementation(projects.data.timetable.viewer)
  implementation(projects.data.timetable.editor)
  implementation(projects.data.lectureevaluation.editor)
  implementation(projects.data.lectureevaluation.viewer)
  implementation(projects.data.lectureevaluation.report)
  implementation(projects.data.lectureevaluation.my)
  implementation(projects.data.user)
  implementation(projects.data.notice)
  implementation(projects.data.signup)

  implementation(projects.domain)
  implementation(projects.di)
  implementation(projects.domain.openmajor)

  implementation(projects.local.openmajor)
  implementation(projects.local.timetable)
  implementation(projects.local.user)

  implementation(projects.remote.lectureevaluation.editor)
  implementation(projects.remote.lectureevaluation.my)
  implementation(projects.remote.lectureevaluation.report)
  implementation(projects.remote.lectureevaluation.viewer)
  implementation(projects.remote.notice)
  implementation(projects.remote.openmajor)
  implementation(projects.remote.signup)
  implementation(projects.remote.timetable)
  implementation(projects.remote.user)

  implementation(projects.presentation)

  implementation(libs.timber)
}
