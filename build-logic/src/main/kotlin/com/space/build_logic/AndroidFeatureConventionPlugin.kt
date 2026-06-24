package com.space.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("movieapp.android.library")
                apply("movieapp.android.compose")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>()
                .named("libs")
            dependencies {
                add("implementation", project(":core:navigation"))
                add("implementation", libs.findBundle("coroutines").get())
                add("implementation", project(":core:ui"))
                add("implementation", libs.findBundle("koin").get())
                add("implementation", libs.findBundle("retrofit").get())
                add("implementation", libs.findLibrary("androidx-navigation3-runtime").get())
                add("implementation", libs.findLibrary("androidx-navigation3-ui").get())
            }
        }
    }
}