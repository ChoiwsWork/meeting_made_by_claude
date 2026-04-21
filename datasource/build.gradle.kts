plugins {
    alias(libs.plugins.cws.android.library)
    alias(libs.plugins.cws.android.hilt)
}

android {
    namespace = "com.cws.meeting.datasource"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.common.analytics)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}
