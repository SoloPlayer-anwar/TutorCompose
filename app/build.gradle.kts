import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp.plugin)
}

android {
    namespace = "com.ka0171729.tutorcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ka0171729.tutorcompose"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        flavorDimensions.add("dev")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    productFlavors {
        create("dev") {
            applicationId = "com.tokyo.ecommerce"
            buildConfigField(type = "String", name = "BASE_URL", value = "\"${properties.getProperty("BASE_URL")}\"")
            buildConfigField(type = "String", name = "CLIENT_KEY", value = "\"${properties.getProperty("CLIENT_KEY")}\"")
            buildConfigField(type = "String", name = "MIDTRANS_URL", value = "\"${properties.getProperty("MIDTRANS_URL")}\"")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    Font
    implementation(libs.font.style)

//    ViewModel
    implementation(libs.androidx.viewmodel)

//    Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core)

//    Retrofit
    implementation(libs.retrofit.android)
    implementation(libs.retrofit.converter)

//    DataStore
    implementation(libs.data.store)

//    Coroutines
    implementation(libs.corotuines.android)
    implementation(libs.corotuines.core)

//    Okhttp3
    implementation(libs.okhttp3.logging)

//    Lottie Animation
    implementation(libs.lottie.animation)

//    Serialization
    implementation(libs.kotlinx.serialization.json)

//    Paging Compose
    implementation(libs.androidx.paging.compose)

//    Navigation Compose
    implementation(libs.navagiation.compose)

//    Splash Screen
    implementation(libs.splash.api)

//    Coil Compose
    implementation(libs.coil.compose)

//    Service Location
    implementation(libs.service.location)

//    Accompanist Permission
    implementation(libs.accompanist.permissions)

//    MidTrans
    implementation(libs.midtrans.android)

//    Room
    implementation(libs.room.runtime.android)
    implementation(libs.room.runtime.compiler)
    implementation(libs.room.ktx)
}