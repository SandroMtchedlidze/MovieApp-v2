package com.example.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {


            val libs = extensions.getByType<VersionCatalogsExtension>()
                .named("libs")


            dependencies {
                add("implementation", libs.findBundle("retrofit").get())
            }
        }
    }
}