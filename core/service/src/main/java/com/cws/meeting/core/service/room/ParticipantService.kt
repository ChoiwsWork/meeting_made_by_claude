package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ConferenceSession
import com.cws.meeting.core.model.Participant
import kotlinx.coroutines.flow.StateFlow

interface ParticipantService {
    val participants: StateFlow<List<Participant>>
    fun start(session: ConferenceSession)
    fun stop()
}
