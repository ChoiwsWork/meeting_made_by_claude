package com.cws.meeting.common.analytics

internal class CompositeAnalyticsHelper(
    private val helpers: List<AnalyticsHelper>,
) : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        helpers.forEach { helper ->
            runCatching { helper.logEvent(event) }
        }
    }
}
