plugins {
    `kotlin-dsl`
}
group = "com.movieapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

}

dependencies {
    testImplementation(libs.junit.junit)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)

}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movieapp.android.application"
            implementationClass = "com.example.build_logic.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "movieapp.android.library"
            implementationClass = "com.example.build_logic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "movieapp.android.compose"
            implementationClass = "com.example.build_logic.AndroidComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "movieapp.android.feature"
            implementationClass = "com.example.build_logic.AndroidFeatureConventionPlugin"
        }
        register("koin") {
            id = "movieapp.koin"
            implementationClass = "com.example.build_logic.KoinConventionPlugin"
        }
        register("retrofit") {
            id = "movieapp.retrofit"
            implementationClass = "com.example.build_logic.RetrofitConventionPlugin"
        }

    }

}