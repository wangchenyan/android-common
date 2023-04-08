pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = java.net.URI("https://maven.aliyun.com/repository/public/") }
        maven { url = java.net.URI("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://maven.aliyun.com/repository/public/") }
        maven { url = java.net.URI("https://jitpack.io") }
    }
}
rootProject.name = "android-common"
include(":app")
include(":common")
