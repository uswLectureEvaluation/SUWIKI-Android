@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.suwiki.android.remote)
}

android {
  namespace = "com.suwiki.remote.timetable"
}

dependencies {
  implementation(projects.data.timetable)

  val bom = libs.firebase.bom
  add("implementation", platform(bom))
  implementation(libs.bundles.firebase)
}
