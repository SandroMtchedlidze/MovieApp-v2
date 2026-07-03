import java.util.Properties

plugins {
    alias(libs.plugins.movie.android.application)
    alias(libs.plugins.movie.android.compose)
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
android {
    namespace = "com.space.movieapp"

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            buildConfigField(
                "String", "TMDB_TOKEN",
                "\"${localProperties["TMDB_TOKEN"]}\""
            )
        }
        release {
            buildConfigField(
                "String", "TMDB_TOKEN",
                "\"${localProperties["TMDB_TOKEN"]}\""
            )
        }
    }
}
dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(projects.core.ui)
    implementation(projects.feature.home)
    implementation(projects.feature.favourites)
    implementation(projects.feature.home.data)
    implementation(projects.feature.home.presentation)
    implementation(projects.feature.home.api)
    implementation(projects.core.networking)
    implementation(projects.feature.movieDetails)
    implementation(projects.feature.movieDetails.data)
    implementation(projects.feature.movieDetails.presentation)
    implementation(projects.feature.movieDetails.api)
}