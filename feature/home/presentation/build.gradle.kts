plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.home.presentaton"
}
dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.feature.home.api)
    implementation(projects.feature.movieDetails.api)
}