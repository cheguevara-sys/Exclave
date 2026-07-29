plugins {
    id("com.android.application")
    id("kotlin-android")         // ← Add this
    id("kotlin-parcelize")
    alias(libs.plugins.protobuf)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aboutlibraries)
}

// setupApp()   // ← DELETE or comment this line

android {
    namespace = "io.nekohasekai.sagernet"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}