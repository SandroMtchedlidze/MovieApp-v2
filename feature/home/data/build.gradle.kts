plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.home.data"
}

dependencies {
    implementation(project(":feature:home:domain"))
    implementation(libs.retrofit.kotlinx.serialization)
}