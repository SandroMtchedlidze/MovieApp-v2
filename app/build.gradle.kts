plugins {
    id("movieapp.android.application")
    id("movieapp.android.compose")
    id("movieapp.koin")
}

android {
    namespace = "com.example.movieapp"
}

dependencies {
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}