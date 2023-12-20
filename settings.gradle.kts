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
include(":app-compose")

include(":core:android")
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
include(":remote:login")

include(":local:openmajor")
include(":local:timetable")
include(":local:user")
include(":local:login")

include(":data:openmajor")
include(":data:timetable")
include(":data:lectureevaluation:viewerreporter")
include(":data:lectureevaluation:editor")
include(":data:lectureevaluation:my")
include(":data:user")
include(":data:notice")
include(":data:signup")
include(":data:login")

include(":domain:openmajor")
include(":domain:user")
include(":domain:signup")
include(":domain:lectureevaluation:viewerreporter")
include(":domain:lectureevaluation:my")
include(":domain:lectureevaluation:editor")
include(":domain:timetable")
include(":domain:notice")
include(":domain:login")

include(":feature:navigator")
include(":feature:openmajor")
include(":feature:timetable")
include(":feature:lectureevaluation:viewerreporter")
include(":feature:lectureevaluation:editor")
include(":feature:lectureevaluation:my")
include(":feature:myinfo")
include(":feature:notice")
include(":feature:signup")
include(":feature:login")
