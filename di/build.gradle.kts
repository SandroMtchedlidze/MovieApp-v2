import java.util.Properties
import kotlin.apply

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
    implementation(project(":feature:movie"))
    implementation(libs.retrofit.kotlinx.serialization)
}
