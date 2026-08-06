plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.space.favourites.presentation"

}

dependencies {
    implementation(libs.coil.compose)
    implementation(projects.feature.favourites.api)
    implementation(projects.feature.movieDetails.api)
}