import org.gradle.kotlin.dsl.dependencies

plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.space.navigation"
}
dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
}