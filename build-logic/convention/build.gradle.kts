plugins {
    `kotlin-dsl`
}

group = "com.kunize.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "suwiki.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "suwiki.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
//        register("androidLibrary") {
//            id = "pluu.android.library"
//            implementationClass = "AndroidLibraryConventionPlugin"
//        }
//        register("androidLibraryCompose") {
//            id = "pluu.android.library.compose"
//            implementationClass = "AndroidLibraryComposeConventionPlugin"
//        }
//        register("androidHilt") {
//            id = "pluu.android.hilt"
//            implementationClass = "AndroidHiltConventionPlugin"
//        }
//        register("javaLibrary") {
//            id = "pluu.java.library"
//            implementationClass = "JavaLibraryConventionPlugin"
//        }
    }
}
