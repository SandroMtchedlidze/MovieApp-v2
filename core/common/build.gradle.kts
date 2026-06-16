plugins {
//    id("movieapp.android.library")
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.core.common"
}

dependencies {
    api(libs.bundles.coroutines)
}
