plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("crouter-plugin")
}

android {
    namespace = "top.wangchenyan.common.sample"
    compileSdk = 36

    defaultConfig {
        applicationId = "top.wangchenyan.common.sample"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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

ksp {
    arg("moduleName", project.name)
    // crouter 默认 scheme
    arg("defaultScheme", "app")
    // crouter 默认 host
    arg("defaultHost", "common.android")
}

dependencies {
    ksp("com.github.wangchenyan.crouter:crouter-processor:3.0.0-beta01")
    implementation(project(":common"))
}