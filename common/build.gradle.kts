plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 31
        consumerProguardFile("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.7")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.github.soulqw:SoulPermission:1.3.1")
    implementation("com.geyifeng.immersionbar:immersionbar:3.2.2")
    implementation("com.liulishuo.filedownloader:library:1.7.7")
    implementation("com.kingja.loadsir:loadsir:1.3.8")
    implementation("top.zibin:Luban:1.1.8")
    implementation("com.github.li-xiaojun:XPopup:2.7.9")
    implementation("com.elvishew:xlog:1.10.1")
    api("com.github.wangchenyan.crouter:crouter-api:2.2.1")
    api("com.google.code.gson:gson:2.10.1")
    api("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.blankj:utilcodex:1.31.1")
    api("com.github.bumptech.glide:glide:4.13.2")
    api("com.github.getActivity:ShapeView:8.5")
    api("io.github.scwang90:refresh-layout-kernel:2.0.5")
    api("io.github.scwang90:refresh-header-material:2.0.5")
    api("io.github.scwang90:refresh-footer-classics:2.0.5")
    api("com.github.wangchenyan:radapter3:3.1.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
            }
        }
    }
}
