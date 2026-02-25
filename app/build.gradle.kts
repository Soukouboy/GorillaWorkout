plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.soukouboy.gorillaworkout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.soukouboy.gorillaworkout"
        minSdk = 23
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android Core
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Lifecycle & ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.media3.common.ktx)
    ksp(libs.room.compiler)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // MPAndroidChart for statistics
    implementation(libs.mpandroidchart)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // Material Design 3
    implementation(libs.material.v1130)  // ou version plus récente

    // Autres dépendances...
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")  // Compatible

    implementation("com.google.android.material:material:1.11.0")
    // ...

}