package com.example.build_logic

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
                apply("movieapp.koin")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>()
                .named("libs")

            dependencies {
                add("implementation", libs.findBundle("coroutines").get())
                add("implementation", project(":core:common"))
                add("implementation", project(":core:ui"))
            }
        }
    }
}