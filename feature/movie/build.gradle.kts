plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.feature.movie"
}
dependencies {
    implementation(project(":navigation"))
    implementation(libs.retrofit.kotlinx.serialization)
}