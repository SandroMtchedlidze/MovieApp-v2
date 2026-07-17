plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.movie.details.presentation"

}
dependencies {
    implementation(projects.core.networking)
    implementation(projects.core.presentation)
    implementation(projects.feature.movieDetails.domain)
    implementation(libs.coil.compose)
}