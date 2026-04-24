package com.cws.meeting.core.service.conference

import com.cws.meeting.core.model.Conference
import kotlinx.coroutines.flow.Flow

interface ConferenceRepository {
    fun observeAll(): Flow<List<Conference>>
    fun observeById(id: String): Flow<Conference?>
}
