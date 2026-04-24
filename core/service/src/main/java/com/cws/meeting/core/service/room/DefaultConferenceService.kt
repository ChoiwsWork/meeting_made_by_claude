package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ConferenceInfo
import com.cws.meeting.core.model.ConferenceMode
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultConferenceService @Inject constructor() : ConferenceService {

    private val _info = MutableStateFlow<ConferenceInfo?>(null)
    override val info: StateFlow<ConferenceInfo?> = _info.asStateFlow()

    override fun start(session: ConferenceSession) {
        _info.value = ConferenceInfo(
            agenda = "Agenda for ${session.conferenceId}",
            roomNumber = session.conferenceId,
            mode = ConferenceMode.DISCUSSION,
        )
    }

    override fun stop() {
        _info.value = null
    }
}
