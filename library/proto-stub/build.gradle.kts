plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")   // ← Add this line
}

android {
    namespace = "io.nekohasekai.sagernet.proto"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-javalite:3.25.3")
}