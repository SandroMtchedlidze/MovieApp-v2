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
    implementation(project(":core:ui"))
    implementation(project(":feature:home"))
    implementation(project(":feature:details"))
    implementation(project(":feature:favourites"))
}