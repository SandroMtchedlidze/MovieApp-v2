plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.presentaton"
}
dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.core.networking)
    implementation(projects.core.presentation)
}