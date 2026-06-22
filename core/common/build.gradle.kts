plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.core.common"
}

dependencies {
    api(libs.bundles.coroutines)
    implementation(libs.retrofit.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
}