package com.cws.meeting.common.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    NoOpAnalyticsHelper()
}

@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) {
    DisposableEffect(screenName) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = "screen_view",
                extras = mapOf("screen_name" to screenName),
            ),
        )
        onDispose {}
    }
}
