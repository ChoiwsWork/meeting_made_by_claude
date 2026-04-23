package com.cws.meeting.core.service.conference

import com.cws.meeting.core.model.Conference
import com.cws.meeting.datasource.ConferenceDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultConferenceRepository @Inject constructor(
    private val dataSource: ConferenceDataSource,
) : ConferenceRepository {

    override fun observeAll(): Flow<List<Conference>> = dataSource.observeAll()
}
