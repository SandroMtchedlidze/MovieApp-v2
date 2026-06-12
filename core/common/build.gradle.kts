
plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
    id("movieapp.retrofit")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.bundles.coroutines)
}
