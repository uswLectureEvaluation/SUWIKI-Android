@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.suwiki.core.network"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.security)

    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    androidTestImplementation(libs.junit4)

    val bom = libs.firebase.bom
    add("implementation", platform(bom))
    implementation(libs.bundles.firebase)

    implementation(libs.timber)
}