@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
}

android {
    namespace = "com.suwiki.local.user"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.data.user)
    implementation(projects.core.database)
    implementation(projects.core.security)

    implementation(libs.bundles.coroutine)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
}