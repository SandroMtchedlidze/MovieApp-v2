import java.util.Properties

plugins {
    alias(libs.plugins.movie.android.feature)
}
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
android {
    namespace = "com.space.di"

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            buildConfigField(
                "String", "TMDB_TOKEN",
                "\"${localProperties["TMDB_TOKEN"]}\""
            )
        }
        release {
            buildConfigField(
                "String", "TMDB_TOKEN",
                "\"${localProperties["TMDB_TOKEN"]}\""
            )
        }
    }
}
dependencies {
    implementation(project(":feature:movie:data"))
    implementation(project(":feature:movie:domain"))
    implementation(project(":feature:movie:presentation"))
    implementation(libs.retrofit.kotlinx.serialization)
}