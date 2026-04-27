package com.cws.meeting.common.analytics

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LogcatAnalyticsHelper @Inject constructor() : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        Log.d(TAG, format(event))
    }

    private fun format(event: AnalyticsEvent): String {
        if (event.extras.isEmpty()) return event.type
        val joined = event.extras.entries.joinToString(", ") { (k, v) -> "$k=$v" }
        return "${event.type} { $joined }"
    }

    private companion object {
        const val TAG = "Analytics"
    }
}
