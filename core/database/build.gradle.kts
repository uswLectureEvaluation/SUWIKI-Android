@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
    id("com.google.devtools.ksp")
}

ksp {
    arg("room.schemaLocation", "$rootDir/schemas")
}

android {
    namespace = "com.suwiki.core.database"
}

dependencies {
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.bundles.coroutine)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
}