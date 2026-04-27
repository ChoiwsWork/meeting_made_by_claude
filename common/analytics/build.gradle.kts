plugins {
    alias(libs.plugins.cws.android.library)
    alias(libs.plugins.cws.android.compose)
    alias(libs.plugins.cws.android.hilt)
}

android {
    namespace = "com.cws.meeting.common.analytics"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.core)
}
