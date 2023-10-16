plugins {
    id("suwiki.android.application")
    id("suwiki.android.application.compose")
}

android {
    namespace = "com.kunize.uswtimetable"

    defaultConfig {
        applicationId = "com.kunize.uswtimetable"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.security)

    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.di)
    implementation(projects.local)

    implementation(projects.remote.lectureevaluation.editor)
    implementation(projects.remote.lectureevaluation.my)
    implementation(projects.remote.lectureevaluation.report)
    implementation(projects.remote.lectureevaluation.viewer)
    implementation(projects.remote.notice)
    implementation(projects.remote.openmajor)
    implementation(projects.remote.signup)
    implementation(projects.remote.timetable.editor)
    implementation(projects.remote.user)
}