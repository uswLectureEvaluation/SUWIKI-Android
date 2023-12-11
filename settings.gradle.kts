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
include(":presentation")
include(":app-compose")

include(":core:model")
include(":core:common")
include(":core:network")
include(":core:security")
include(":core:database")
include(":core:designsystem")
include(":core:ui")

include(":remote:openmajor")
include(":remote:timetable")
include(":remote:lectureevaluation:viewerreporter")
include(":remote:lectureevaluation:my")
include(":remote:lectureevaluation:editor")
include(":remote:signup")
include(":remote:notice")
include(":remote:user")

include(":local:openmajor")
include(":local:timetable")
include(":local:user")

include(":data:openmajor")
include(":data:timetable")
include(":data:lectureevaluation:viewerreporter")
include(":data:lectureevaluation:editor")
include(":data:lectureevaluation:my")
include(":data:user")
include(":data:notice")
include(":data:signup")

include(":domain:openmajor")
include(":domain:user")
include(":domain:signup")
include(":domain:lectureevaluation:viewerreporter")
include(":domain:lectureevaluation:my")
include(":domain:lectureevaluation:editor")
include(":domain:timetable")
include(":domain:notice")
include(":core:android")
