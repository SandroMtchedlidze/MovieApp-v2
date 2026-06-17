plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.movie.android.compose)
}

android {
    namespace = "com.space.core.ui"
}
dependencies {
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.coil.compose)
}