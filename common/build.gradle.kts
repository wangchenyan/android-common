plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "top.wangchenyan.common"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 34
        consumerProguardFile("consumer-rules.pro")
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
    api("androidx.appcompat:appcompat:1.7.0")
    api("androidx.fragment:fragment-ktx:1.8.0")
    api("com.google.android.material:material:1.12.0")
    api("com.google.code.gson:gson:2.10.1")
    api("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.blankj:utilcodex:1.31.1")
    api("com.github.bumptech.glide:glide:4.13.2")
    api("com.github.getActivity:ShapeView:9.2")
    api("io.github.scwang90:refresh-layout-kernel:2.0.5")
    api("io.github.scwang90:refresh-header-material:2.0.5")
    api("io.github.scwang90:refresh-footer-classics:2.0.5")
    api("com.github.wangchenyan.crouter:crouter-api:3.0.0-beta01")
    api("com.github.wangchenyan:radapter3:3.1.1")
    api("com.kingja.loadsir:loadsir:1.3.8")
    api("com.geyifeng.immersionbar:immersionbar:3.2.2")
    implementation("com.github.soulqw:SoulPermission:1.4.0")
    implementation("com.liulishuo.filedownloader:library:1.7.7")
    implementation("top.zibin:Luban:1.1.8")
    implementation("com.github.li-xiaojun:XPopup:2.7.9")
    implementation("com.elvishew:xlog:1.10.1")
    compileOnly("com.google.dagger:hilt-android:2.48.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
                groupId = "com.github.wangchenyan"
                artifactId = "android-common"
                version = "local"
            }
        }
    }
}
