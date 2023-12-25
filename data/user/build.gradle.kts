@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.data)
}

android {
  namespace = "com.suwiki.data.user"
}

dependencies {
  implementation(projects.core.security)
  implementation(projects.domain.user)
}
