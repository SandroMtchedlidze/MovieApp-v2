plugins {
    alias(libs.plugins.movie.android.feature)
}
android {
    namespace = "com.space.feature.movie"
}
dependencies {
    implementation(project(":navigation"))
}