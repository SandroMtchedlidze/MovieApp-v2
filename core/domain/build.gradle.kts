plugins {
    alias(libs.plugins.movie.kotlin)
}
dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.networking)
    implementation(libs.androidx.paging.common)
}
