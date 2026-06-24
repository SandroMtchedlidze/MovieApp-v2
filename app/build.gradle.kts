plugins {
    alias(libs.plugins.movie.android.application)
    alias(libs.plugins.movie.android.compose)
}

android {
    namespace = "com.space.movieapp"
}
dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(projects.core.ui)
    implementation(projects.feature.home)
    implementation(projects.feature.details)
    implementation(projects.feature.favourites)
    implementation(projects.feature.home.data)
    implementation(projects.feature.home.presentation)
    implementation(projects.feature.home.api)
}