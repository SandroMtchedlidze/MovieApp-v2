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

rootProject.name = "MovieApp"

include(":app")

includeAllModules("core", "feature")

fun includeAllModules(vararg groupDirs: String) {
    groupDirs.forEach { group ->
        File(rootDir, group).listFiles()?.filter { it.isDirectory }?.forEach {
            include(":$group:${it.name}")
        }
    }
}
