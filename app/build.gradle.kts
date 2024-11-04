@file:Suppress("UNUSED_EXPRESSION")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.hivemind"
    compileSdk = 35 // Updated to the latest stable version

    defaultConfig {
        applicationId = "com.example.hivemind"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 35 // Updated to the latest stable version
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Ensure this matches the Compose version
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose dependencies
    "1.5.3"
    //noinspection GradleDependency
    implementation(libs.ui)
    implementation(libs.material3)
    //noinspection GradleDependency
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.materialIconsExtended)



            // Testing dependencies
    testImplementation(libs.junit)
    //noinspection GradleDependency
    androidTestImplementation(libs.ui.test.junit4)
    //noinspection GradleDependency
    debugImplementation(libs.ui.tooling)
}