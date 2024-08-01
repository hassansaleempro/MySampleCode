plugins {
    alias(libs.plugins.androidApplication) // Plugin for Android application projects
    alias(libs.plugins.jetbrainsKotlinAndroid) // Plugin for Kotlin Android
    alias(libs.plugins.daggerHilt) // Use alias to apply the Hilt plugin
    id("kotlin-kapt") // Make sure KAPT is enabled for annotation processing
}

android {
    namespace = "com.sample.code"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.code"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packagingOptions {
        // Resolve the file conflict by picking the first occurrence
        pickFirst("META-INF/gradle/incremental.annotation.processors")
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Core libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Testing libraries

    // Logic test
    testImplementation(libs.junit)
    // JVM test
    androidTestImplementation(libs.androidx.junit)
    // UI test
    androidTestImplementation(libs.androidx.espresso.core)
    // Coroutines test
    testImplementation(libs.kotlinx.coroutines.test)
    // AndroidX Arch Core for LiveData testing
    testImplementation(libs.androidx.core.testing)
    // Turbine for testing Flows
    testImplementation(libs.turbine)
    // MockK for mocking Kotlin objects
    testImplementation(libs.mockk)
    // Kotlin Test for assertions
    testImplementation(libs.kotlin.test)
    // Mockito for Java object mocking
    testImplementation(libs.mockito.core)



    // Android Architecture Components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Dagger Hilt for dependency injection
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.compiler)
    kapt(libs.hilt.android.compiler)


    // loading dialog
    implementation(libs.android.spinKit)



}
