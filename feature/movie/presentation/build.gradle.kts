plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.presentaton"
}
dependencies {
    implementation(project(":feature:movie:domain"))
    implementation(project(":core:networking"))
    implementation(project(":core:presentation"))
}