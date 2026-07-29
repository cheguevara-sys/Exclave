plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")   // ← Add this
}

android {
    namespace = "io.nekohasekai.sagernet.plugin.naive"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.nekohasekai.sagernet.plugin.naive"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
}