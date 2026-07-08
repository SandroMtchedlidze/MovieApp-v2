plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.space.presentation"
}
dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(projects.core.networking)
}