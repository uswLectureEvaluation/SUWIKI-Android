package com.kunize.convention

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import java.util.Optional

internal fun <T> DependencyHandler.implementation(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("implementation", dependencyNotation.get())

internal fun DependencyHandler.implementation(
    dependencyNotation: Any,
): Dependency? = add("implementation", dependencyNotation)

internal fun <T> DependencyHandler.kapt(
    dependencyNotation: Optional<Provider<T>>,
): Dependency? = add("kapt", dependencyNotation.get())