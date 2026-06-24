plugins {
    alias(libs.plugins.movie.kotlin)
}
dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(project(":core:networking"))
}