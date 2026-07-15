plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.home.presentaton"
}
dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.core.networking)
    implementation(projects.core.presentation)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.feature.home.api)
    implementation(projects.core.navigation)
    implementation(projects.feature.movieDetails.api)
}