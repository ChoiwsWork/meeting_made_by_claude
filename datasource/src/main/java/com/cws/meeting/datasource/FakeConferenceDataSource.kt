@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.datasource

import com.cws.meeting.core.model.Conference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

@Singleton
class FakeConferenceDataSource @Inject constructor() : ConferenceDataSource {

    private val now = Clock.System.now()

    private val state = MutableStateFlow(
        listOf(
            Conference(
                id = "conf-1",
                title = "Weekly sync",
                scheduledAt = now + 1.hours,
                hostName = "Wooseok",
            ),
            Conference(
                id = "conf-2",
                title = "Architecture review",
                scheduledAt = now + 4.hours,
                hostName = "Jihye",
            ),
            Conference(
                id = "conf-3",
                title = "Retrospective",
                scheduledAt = now + 24.hours,
                hostName = "Minsu",
            ),
        )
    )

    override fun observeAll(): Flow<List<Conference>> = state.asStateFlow()
}
