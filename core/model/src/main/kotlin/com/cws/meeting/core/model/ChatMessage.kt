@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.core.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class ChatMessage(
    val id: String,
    val senderId: String,
    val content: String,
    val sentAt: Instant,
)
