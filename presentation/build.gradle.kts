plugins {
  id("suwiki.android.feature.compose")
}

android {
  namespace = "com.mangbaam.presentation"
}

dependencies {
  implementation(projects.core.model)

  implementation(projects.domain.openmajor)
  implementation(projects.domain.signup)
  implementation(projects.domain.user)
}
