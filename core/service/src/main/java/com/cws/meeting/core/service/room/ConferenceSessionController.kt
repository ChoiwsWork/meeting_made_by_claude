package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ConferenceSession
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConferenceSessionController @Inject constructor(
    private val conferenceService: ConferenceService,
    private val participantService: ParticipantService,
    private val chatService: ChatService,
) {

    fun start(session: ConferenceSession) {
        conferenceService.start(session)
        participantService.start(session)
        chatService.start(session)
    }

    fun stop() {
        chatService.stop()
        participantService.stop()
        conferenceService.stop()
    }
}
