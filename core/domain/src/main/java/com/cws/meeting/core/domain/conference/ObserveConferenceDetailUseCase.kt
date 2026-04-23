package com.cws.meeting.core.domain.conference

import com.cws.meeting.core.model.Conference
import com.cws.meeting.core.service.conference.ConferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConferenceDetailUseCase @Inject constructor(
    private val repository: ConferenceRepository,
) {
    operator fun invoke(id: String): Flow<Conference?> = repository.observeById(id)
}
