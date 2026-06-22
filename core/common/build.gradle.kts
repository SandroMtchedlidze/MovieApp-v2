plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.movie.android.feature)
}
android {
    namespace = "com.space.core.common"
}

dependencies {
    api(libs.bundles.coroutines)
    implementation(libs.retrofit.core)
}
