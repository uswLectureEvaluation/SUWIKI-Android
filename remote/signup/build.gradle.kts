@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.suwiki.signup"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":data"))

    implementation(libs.bundles.coroutine)
    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.junit4)

    implementation(libs.timber)
}