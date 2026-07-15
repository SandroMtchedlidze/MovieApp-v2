plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.space.favourites.presentation"

}

dependencies {
    implementation(projects.core.networking)
    implementation(projects.core.presentation)
    implementation(libs.coil.compose)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.feature.favourites.api)
    implementation(projects.feature.movieDetails.api)
    implementation(projects.core.navigation)
}