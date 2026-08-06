plugins {
    alias(libs.plugins.movie.android.feature)
}

android {
    namespace = "com.space.home.di"
}

dependencies {
    implementation(projects.feature.home.presentation)
    implementation(projects.feature.home.data)
}