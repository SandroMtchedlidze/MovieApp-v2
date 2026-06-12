plugins {
    id("movieapp.android.library")
    id("movieapp.android.compose")
}

android {
    namespace = "com.example.core_ui"
}
dependencies {
    implementation("androidx.compose.ui:ui-text-google-fonts:1.11.1")
}