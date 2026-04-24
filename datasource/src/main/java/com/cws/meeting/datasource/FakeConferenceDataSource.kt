@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.datasource

import com.cws.meeting.core.model.Conference
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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
                host = FakeUserDataSource.CurrentUser,
                attendees = listOf(
                    FakeUserDataSource.CurrentUser,
                    FakeUserDataSource.Jihye,
                    FakeUserDataSource.Minsu,
                    FakeUserDataSource.Haeun,
                ),
            ),
            Conference(
                id = "conf-2",
                title = "Architecture review",
                scheduledAt = now + 4.hours,
                host = FakeUserDataSource.Jihye,
                attendees = listOf(
                    FakeUserDataSource.Jihye,
                    FakeUserDataSource.CurrentUser,
                    FakeUserDataSource.Dowon,
                ),
            ),
            Conference(
                id = "conf-3",
                title = "Retrospective",
                scheduledAt = now + 24.hours,
                host = FakeUserDataSource.Minsu,
                attendees = listOf(
                    FakeUserDataSource.Minsu,
                    FakeUserDataSource.CurrentUser,
                    FakeUserDataSource.Jihye,
                    FakeUserDataSource.Haeun,
                    FakeUserDataSource.Dowon,
                ),
            ),
        )
    )

    override fun observeAll(): Flow<List<Conference>> = state.asStateFlow()

    override fun observeById(id: String): Flow<Conference?> =
        state.map { conferences -> conferences.firstOrNull { it.id == id } }

    override suspend fun joinConference(id: String): Result<ConferenceSession> {
        delay(800)
        val conference = state.value.firstOrNull { it.id == id }
            ?: return Result.failure(NoSuchElementException("Conference $id not found"))
        return Result.success(
            ConferenceSession(
                sessionId = "session-${conference.id}",
                conferenceId = conference.id,
            )
        )
    }
}
