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
    implementation(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movieapp.android.application"
            implementationClass = "com.space.build_logic.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "movieapp.android.library"
            implementationClass = "com.space.build_logic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "movieapp.android.compose"
            implementationClass = "com.space.build_logic.AndroidComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "movieapp.android.feature"
            implementationClass = "com.space.build_logic.AndroidFeatureConventionPlugin"
        }
        register("kotlinLibrary") {
            id= "movieapp.kotlin.library"
            implementationClass = "com.space.build_logic.KotlinLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = "movieapp.android.room"
            implementationClass = "com.space.build_logic.AndroidRoomConventionPlugin"
        }
    }
}