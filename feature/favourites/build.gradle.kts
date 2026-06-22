plugins {
    alias(libs.plugins.movie.android.feature)
}

android {
    namespace = "com.space.feature.favourites"
}
dependencies {
    implementation(project(":core:navigation"))
}