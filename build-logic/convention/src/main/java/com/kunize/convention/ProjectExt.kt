package com.kunize.convention

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

@Suppress("RemoveRedundantBackticks")
internal fun Project.`java`(
    configure: Action<JavaPluginExtension>,
) {
    (this as ExtensionAware).extensions.configure("java", configure)
}

@Suppress("RemoveRedundantBackticks")
internal fun Project.`kotlin`(
    configure: Action<KotlinJvmProjectExtension>,
) {
    (this as ExtensionAware).extensions.configure("kotlin", configure)
}

@Suppress("RemoveRedundantBackticks")
internal fun Project.`kapt`(
    configure: Action<KaptExtension>,
) {
    (this as ExtensionAware).extensions.configure("kapt", configure)
}

@Suppress("RemoveRedundantBackticks")
internal fun Project.`ksp`(
    configure: Action<KaptExtension>,
) {
    (this as ExtensionAware).extensions.configure("kapt", configure)
}
