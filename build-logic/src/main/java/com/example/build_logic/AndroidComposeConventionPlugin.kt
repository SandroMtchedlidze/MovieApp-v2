package com.example.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import com.android.build.gradle.LibraryExtension
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.kotlin.dsl.findByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            pluginManager.withPlugin("com.android.application") {
                extensions.findByType<ApplicationExtension>()?.buildFeatures {
                    compose = true
                }
            }

            pluginManager.withPlugin("com.android.library") {
                extensions.findByType<LibraryExtension>()?.buildFeatures {
                    compose = true
                }
            }



            val libs = extensions.getByType<VersionCatalogsExtension>()
                .named("libs")

            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findBundle("compose").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())

            }

        }
    }
}