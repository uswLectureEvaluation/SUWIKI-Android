import com.kunize.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

internal class FeatureComposeConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("suwiki.android.library")
        apply("suwiki.android.library.compose")
        apply("suwiki.android.hilt")
      }

      dependencies {
        "implementation"(project(":core:model"))
        "implementation"(project(":core:android"))
        "implementation"(project(":core:ui"))
        "implementation"(project(":core:designsystem"))

        "implementation"(libs.findBundle("orbit").get())

        "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
        "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())

        "androidTestImplementation"(libs.findLibrary("junit").get())
        "implementation"(libs.findLibrary("timber").get())
      }
    }
  }
}
