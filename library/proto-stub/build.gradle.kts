plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "io.nekohasekai.sagernet.proto"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

// setupCommon()   // ← Commented out (this was causing the error)

dependencies {
    implementation("com.google.protobuf:protobuf-javalite:3.25.3")
}