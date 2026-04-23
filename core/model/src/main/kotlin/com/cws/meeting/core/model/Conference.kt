@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.core.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Conference(
    val id: String,
    val title: String,
    val scheduledAt: Instant,
    val host: User,
    val attendees: List<User>,
)
