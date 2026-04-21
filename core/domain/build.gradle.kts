plugins {
    alias(libs.plugins.cws.android.library)
    alias(libs.plugins.cws.android.hilt)
}

android {
    namespace = "com.cws.meeting.core.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.service)
    implementation(projects.common.analytics)

    implementation(libs.kotlinx.coroutines.core)
}
