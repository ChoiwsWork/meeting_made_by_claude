package com.cws.meeting.core.model

data class User(
    val id: String,
    val displayName: String,
    val email: String? = null,
)
