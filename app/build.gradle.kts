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
    implementation(project(":core:ui"))
}
