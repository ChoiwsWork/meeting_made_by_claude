package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ConferenceInfo
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.flow.StateFlow

interface ConferenceService {
    val info: StateFlow<ConferenceInfo?>
    fun start(session: ConferenceSession)
    fun stop()
}
