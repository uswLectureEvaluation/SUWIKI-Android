@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.data)
}

android {
  namespace = "com.suwiki.data.lectureevalution.my"
}

dependencies {
  implementation(projects.domain.lectureevaluation.my)
}
