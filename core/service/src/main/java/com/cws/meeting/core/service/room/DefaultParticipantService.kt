package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ConferenceSession
import com.cws.meeting.core.model.Participant
import com.cws.meeting.core.model.ParticipantRole
import com.cws.meeting.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultParticipantService @Inject constructor() : ParticipantService {

    private val _participants = MutableStateFlow<List<Participant>>(emptyList())
    override val participants: StateFlow<List<Participant>> = _participants.asStateFlow()

    override fun start(session: ConferenceSession) {
        _participants.value = listOf(
            Participant(
                user = User(id = "u1", displayName = "Wooseok", email = "wooseok@example.com"),
                role = ParticipantRole.HOST,
            ),
            Participant(
                user = User(id = "u2", displayName = "Jihye", email = null),
                role = ParticipantRole.PRESENTER,
            ),
            Participant(
                user = User(id = "u3", displayName = "Minsu", email = null),
                role = ParticipantRole.ATTENDEE,
            ),
        )
    }

    override fun stop() {
        _participants.value = emptyList()
    }
}
