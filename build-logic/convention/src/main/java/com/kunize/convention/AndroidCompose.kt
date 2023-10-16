package com.kunize.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().requiredVersion
        }

        dependencies {
            // Disabling to work with Alpha
            "api"(platform(libs.findLibrary("compose.bom").get()))
            "implementation"(libs.findBundle("compose").get())
            "debugImplementation"(libs.findBundle("compose.debug").get())
        }
    }
}