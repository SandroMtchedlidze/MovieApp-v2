plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.movie.home.api"
}
dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(projects.core.navigation)
    implementation(libs.kotlinx.serialization.json)
}