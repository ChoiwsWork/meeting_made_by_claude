plugins {
    alias(libs.plugins.cws.android.application)
    alias(libs.plugins.cws.android.compose)
    alias(libs.plugins.cws.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.cws.meeting"

    defaultConfig {
        applicationId = "com.cws.meeting"
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
}

dependencies {
    implementation(projects.feature)
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.service)
    implementation(projects.common.designsystem)
    implementation(projects.common.analytics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
