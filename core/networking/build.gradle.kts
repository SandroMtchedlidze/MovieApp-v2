plugins {
    alias(libs.plugins.movie.kotlin)
    alias(libs.plugins.kotlin.serialization)
}
dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.koin.core)
    implementation(libs.okhttp.core)
    implementation(libs.kotlinx.serialization.json)
}