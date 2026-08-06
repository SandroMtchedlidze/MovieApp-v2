plugins {
    alias(libs.plugins.movie.android.feature)
}

android {
    namespace = "com.space.movie.details.di"

}

dependencies {
    implementation(projects.feature.movieDetails.presentation)
    implementation(projects.feature.movieDetails.data)
}