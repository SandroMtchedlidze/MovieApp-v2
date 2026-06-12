pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}



dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
    versionCatalogs {
        create("libs") {
            from(files("C:\\Users\\sandro.mtchedlidze\\AndroidStudioProjects\\MovieApp2\\gradle\\libs.versions.toml"))
        }

    }
}

rootProject.name = "build-logic"