pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "MovieApp"

include(":app")

includeAllModules("core", "feature")

fun includeAllModules(vararg groupDirs: String) {
    val rootDir = settings.rootDir
    groupDirs.forEach { group ->
        File(rootDir, group).walkTopDown()
            .filter { it.isDirectory && File(it, "build.gradle.kts").exists() }
            .forEach {
                val relativePath = it.relativeTo(rootDir).path.replace(File.separator, ":")
                include(":$relativePath")
            }
    }
}
include(":feature:home:api")
include(":feature:movie_details:data")
include(":feature:movie_details:domain")
include(":feature:movie_details:presentation")
include(":feature:movie_details:api")
