plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("auto-register")
}

android {
    namespace = "top.wangchenyan.android.common"
    compileSdk = 34

    defaultConfig {
        applicationId = "top.wangchenyan.android.common"
        minSdk = 23
        targetSdk = 34
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

autoregister {
    registerInfo = listOf(
        // crouter 注解收集
        mapOf(
            "scanInterface" to "me.wcy.router.annotation.RouteLoader",
            "codeInsertToClassName" to "me.wcy.router.RouteSet",
            "codeInsertToMethodName" to "init",
            "registerMethodName" to "register",
            "include" to listOf("me/wcy/router/annotation/loader/.*")
        )
    )
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    ksp("com.github.wangchenyan.crouter:crouter-compiler:2.4.0-beta01")
    implementation(project(":common"))
}