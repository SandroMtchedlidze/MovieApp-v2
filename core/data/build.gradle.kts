plugins {
    alias(libs.plugins.movie.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.space.data"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(projects.core.domain)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.bundles.koin)
    ksp(libs.room.compiler)
}