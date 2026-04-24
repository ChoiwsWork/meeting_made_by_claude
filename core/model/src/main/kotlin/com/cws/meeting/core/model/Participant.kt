package com.cws.meeting.core.model

data class Participant(
    val user: User,
    val role: ParticipantRole,
)
