package com.cws.meeting.datasource

import com.cws.meeting.core.model.Conference
import kotlinx.coroutines.flow.Flow

interface ConferenceDataSource {
    fun observeAll(): Flow<List<Conference>>
    fun observeById(id: String): Flow<Conference?>
}
