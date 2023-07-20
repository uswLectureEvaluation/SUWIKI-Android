package com.kunize.convention

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

@Suppress("RemoveRedundantBackticks")
internal fun Project.`kapt`(
    configure: Action<KaptExtension>,
) {
    (this as ExtensionAware).extensions.configure("kapt", configure)
}