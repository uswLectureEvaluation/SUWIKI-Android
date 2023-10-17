enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "uswtimetable"
include(":domain")
include(":data")
include(":presentation")
include(":app-compose")
include(":core:model")
include(":local")
include(":di")
include(":core:network")
include(":core:security")
include(":remote:openmajor")
include(":remote:timetable:editor")
include(":remote:lectureevaluation:viewer")
include(":remote:lectureevaluation:report")
include(":remote:lectureevaluation:my")
include(":remote:lectureevaluation:editor")
include(":remote:signup")
include(":remote:notice")
include(":remote:user")
include(":core:database")
include(":local:openmajor")
include(":local:timetable:viewer")
include(":local:timetable:editor")
