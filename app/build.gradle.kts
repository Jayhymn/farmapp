plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.jayhymn.farmapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jayhymn.farmapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    // for room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.testng)
    testImplementation(libs.hilt.android.testing)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // for dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.lottie)

    implementation(libs.androidx.core.splashscreen)

    testImplementation (libs.androidx.core.testing)

    // For Coroutine testing
    testImplementation (libs.kotlinx.coroutines.test)

    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //hilt testing
    androidTestImplementation (libs.hilt.android.testing)
    kaptAndroidTest (libs.hilt.compiler)
    
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    // Mockito for mocking
    androidTestImplementation("org.mockito:mockito-core:5.0.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.0.0") // For JUnit 5 support

    // AndroidX Test - UI Testing
    androidTestImplementation("androidx.test:runner:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
}