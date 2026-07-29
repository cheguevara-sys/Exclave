plugins {
    id("com.android.application")
    // id("kotlin-android")   // ← REMOVED (no longer needed)
    id("kotlin-parcelize")
    alias(libs.plugins.protobuf)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aboutlibraries)
}

setupApp()

android {
    namespace = "io.nekohasekai.sagernet"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {                     // ← This still works without the plugin
        jvmTarget = "17"
    }
}

ksp {
    arg("room.incremental", "true")
    arg("room.schemaLocation", "$projectDir/schemas")
}

aboutLibraries {
    offlineMode = true
    collect {
        configPath = file("src/main/aboutlibraries")
        includePlatform = true
    }
    export {
        outputFile = file("src/main/res/raw/aboutlibraries.json")
        excludeFields.addAll("name", "description", "developers", "funding", "licenses", "organization", "scm", "website", "License")
        prettyPrint = true
    }
}

dependencies {
    implementation(fileTree("libs"))
    implementation(project(":library:proto-stub"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.core.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.camera2)
    implementation(libs.swiperefreshlayout)
    implementation(libs.appcompat)
    implementation(libs.preference)
    implementation(libs.flexbox)
    implementation(libs.work.runtime.ktx)
    implementation(libs.work.multiprocess)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.zxing.core)
    implementation(libs.snakeyaml)
    implementation(libs.material.about.library)
    implementation(libs.process.phoenix)
    implementation(libs.kryo)
    implementation(libs.jini.lib)
    implementation(libs.markwon.core)
    implementation(libs.recyclerview.fastscroll)
    implementation(libs.editorkit)
    implementation(libs.editorkit.language.json)

    // Protobuf
    implementation("com.google.protobuf:protobuf-javalite:3.25.3")

    // JSch for SSH tunneling
    implementation("com.jcraft:jsch:0.1.55")

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}