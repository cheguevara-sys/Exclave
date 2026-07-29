plugins {
    id("java-gradle-plugin")
    id("kotlin-dsl")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.8.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
}

gradlePlugin {
    plugins {
        create("setupApp") {
            id = "com.exclave.setup"
            implementationClass = "SetupAppPlugin"
        }
    }
}