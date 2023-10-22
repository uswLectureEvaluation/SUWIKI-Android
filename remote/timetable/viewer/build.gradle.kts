@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("suwiki.android.remote")
}

android {
  namespace = "com.suwiki.remote.timetable.viewer"
}

dependencies {
  implementation(projects.data.timetable.viewer)

  val bom = libs.firebase.bom
  add("implementation", platform(bom))
  implementation(libs.bundles.firebase)
}
