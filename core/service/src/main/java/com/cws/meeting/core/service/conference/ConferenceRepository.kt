package com.cws.meeting.core.service.conference

import com.cws.meeting.core.model.Conference
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.flow.Flow

interface ConferenceRepository {
    fun observeAll(): Flow<List<Conference>>
    fun observeById(id: String): Flow<Conference?>
    suspend fun join(id: String): Result<ConferenceSession>
}
