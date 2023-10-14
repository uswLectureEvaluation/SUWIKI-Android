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
include (":domain")
include (":data")
include (":presentation")
include (":app-compose")
include(":remote")
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
