plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.compose)
}
android {
    namespace = "com.space.navigation"
}
dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.json)
}