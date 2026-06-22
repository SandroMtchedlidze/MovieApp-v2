plugins {
    alias(libs.plugins.movie.android.feature)
}

android {
    namespace = "com.space.feature.details"
}
dependencies {
    implementation(project(":core:navigation"))
}