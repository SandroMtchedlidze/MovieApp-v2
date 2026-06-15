plugins {
    id("movieapp.android.application")
    id("movieapp.android.compose")

}

android {
    namespace = "com.space.movieapp"
}

dependencies {
    implementation(libs.androidx.activity.compose)

}
