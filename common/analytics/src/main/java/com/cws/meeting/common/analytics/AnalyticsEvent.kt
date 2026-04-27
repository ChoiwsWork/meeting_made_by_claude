package com.cws.meeting.common.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: Map<String, String> = emptyMap(),
)
