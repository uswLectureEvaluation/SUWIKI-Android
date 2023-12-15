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
  implementation(projects.presentation)

  implementation(projects.core.android)
  implementation(projects.core.model)
  implementation(projects.core.common)
  implementation(projects.core.network)
  implementation(projects.core.security)
  implementation(projects.core.database)
  implementation(projects.core.ui)

  implementation(projects.remote.openmajor)
  implementation(projects.remote.timetable)
  implementation(projects.remote.lectureevaluation.viewerreporter)
  implementation(projects.remote.lectureevaluation.my)
  implementation(projects.remote.lectureevaluation.editor)
  implementation(projects.remote.signup)
  implementation(projects.remote.notice)
  implementation(projects.remote.user)

  implementation(projects.local.openmajor)
  implementation(projects.local.timetable)
  implementation(projects.local.user)

  implementation(projects.data.openmajor)
  implementation(projects.data.timetable)
  implementation(projects.data.lectureevaluation.viewerreporter)
  implementation(projects.data.lectureevaluation.editor)
  implementation(projects.data.lectureevaluation.my)
  implementation(projects.data.user)
  implementation(projects.data.notice)
  implementation(projects.data.signup)

  implementation(projects.domain.openmajor)
  implementation(projects.domain.user)
  implementation(projects.domain.signup)
  implementation(projects.domain.lectureevaluation.viewerreporter)
  implementation(projects.domain.lectureevaluation.my)
  implementation(projects.domain.lectureevaluation.editor)
  implementation(projects.domain.timetable)
  implementation(projects.domain.notice)

  implementation(libs.timber)
}
