plugins {
    id("suwiki.android.library")
    id("suwiki.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.suwiki.remote"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.junit4)

    val bom = libs.firebase.bom
    add("implementation", platform(bom))
    implementation(libs.bundles.firebase)

    implementation(libs.timber)
}
