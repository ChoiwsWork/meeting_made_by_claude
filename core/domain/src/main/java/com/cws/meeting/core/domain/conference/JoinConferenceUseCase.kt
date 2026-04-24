package com.cws.meeting.core.domain.conference

import com.cws.meeting.core.model.ConferenceSession
import com.cws.meeting.core.service.conference.ConferenceRepository
import javax.inject.Inject

class JoinConferenceUseCase @Inject constructor(
    private val repository: ConferenceRepository,
) {
    suspend operator fun invoke(id: String): Result<ConferenceSession> =
        repository.join(id)
}
