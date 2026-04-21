plugins {
    alias(libs.plugins.cws.android.library)
    alias(libs.plugins.cws.android.compose)
    alias(libs.plugins.cws.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.cws.meeting.feature"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)
    // Direct access to :core:service is allowed for simple read-only queries only.
    // Anything with business logic should go through :core:domain UseCases.
    implementation(projects.core.service)
    implementation(projects.common.designsystem)
    implementation(projects.common.analytics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
