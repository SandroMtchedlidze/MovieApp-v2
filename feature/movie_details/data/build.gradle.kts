plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.space.data"
}

dependencies {
    implementation(projects.core.networking)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(projects.feature.movieDetails.domain)
}