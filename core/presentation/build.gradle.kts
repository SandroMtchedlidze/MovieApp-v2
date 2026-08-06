plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.movie.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.space.presentation"
}
dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(projects.core.networking)
    implementation(libs.bundles.koin)
    implementation(projects.core.navigation)
}