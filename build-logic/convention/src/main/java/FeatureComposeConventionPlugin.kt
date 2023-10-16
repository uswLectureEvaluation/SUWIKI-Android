import com.kunize.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class FeatureComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("suwiki.android.library")
                apply("suwiki.android.hilt")
            }

            dependencies {
                "implementation"(project(":domain"))

                "implementation"(libs.findBundle("androidx.compose").get())

                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())

                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}
