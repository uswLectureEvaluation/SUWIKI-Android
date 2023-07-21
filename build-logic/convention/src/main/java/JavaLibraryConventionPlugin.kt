import com.kunize.convention.Const
import com.kunize.convention.java
import com.kunize.convention.kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }

            java {
                sourceCompatibility = Const.JAVA_VERSION
                targetCompatibility = Const.JAVA_VERSION
            }

            kotlin {
                jvmToolchain(Const.JDK_VERSION)
            }
        }
    }
}
