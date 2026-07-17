import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.movie.android.feature)
    alias(libs.plugins.kotlin.serialization)
}
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
android {
    namespace = "com.space.data"

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
    implementation(project(":feature:home:domain"))
    implementation(project(":core:networking"))
    implementation(libs.retrofit.kotlinx.serialization)
}