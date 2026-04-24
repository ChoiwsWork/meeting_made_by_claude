package com.cws.meeting.datasource

import com.cws.meeting.core.model.Conference
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.flow.Flow

interface ConferenceDataSource {
    fun observeAll(): Flow<List<Conference>>
    fun observeById(id: String): Flow<Conference?>
    suspend fun joinConference(id: String): Result<ConferenceSession>
}
