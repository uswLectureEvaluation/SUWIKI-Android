import com.kunize.convention.implementation
import com.kunize.convention.kapt
import com.kunize.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("kotlin-kapt")
            }

            dependencies {
                implementation(libs.findLibrary("hilt.android"))
                kapt(libs.findLibrary("hilt.compiler"))
            }

            kapt {
                correctErrorTypes = true
            }
        }
    }
}
