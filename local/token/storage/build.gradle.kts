@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
}

android {
    namespace = "com.suwiki.token.storage"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.data)
    implementation(projects.core.security)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
}