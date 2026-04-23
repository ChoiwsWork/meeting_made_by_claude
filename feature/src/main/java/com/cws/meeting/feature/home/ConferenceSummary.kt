@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.feature.home

import com.cws.meeting.core.model.Conference
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class ConferenceSummary(
    val id: String,
    val title: String,
    val scheduledAt: Instant,
    val hostDisplayName: String,
)

fun Conference.toSummary(): ConferenceSummary = ConferenceSummary(
    id = id,
    title = title,
    scheduledAt = scheduledAt,
    hostDisplayName = host.displayName,
)
