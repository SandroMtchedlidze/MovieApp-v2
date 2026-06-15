
plugins {
    id("movieapp.android.library")
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.core_common"
}

dependencies {
    api(libs.bundles.coroutines)
}
