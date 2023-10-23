plugins {
  id("suwiki.android.feature.compose")
}

android {
  namespace = "com.mangbaam.presentation"
}

dependencies {
  implementation(projects.domain.openmajor)
}
