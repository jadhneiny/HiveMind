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
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.materialIconsExtended)

    // Retrofit and Gson for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit core
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson converter
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0") // Logging interceptor for debugging

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
}
