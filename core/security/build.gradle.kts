@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.suwiki.security"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.bundles.coroutine)

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)

    ksp(libs.encrypted.datastore.preference.ksp)
    implementation(libs.encrypted.datastore.preference.ksp.annotations)
    implementation(libs.encrypted.datastore.preference.security)

    implementation(libs.timber)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
}