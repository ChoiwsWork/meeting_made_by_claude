package com.cws.meeting.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cws.meeting.common.analytics.AnalyticsEvent
import com.cws.meeting.common.analytics.AnalyticsHelper
import com.cws.meeting.core.domain.conference.JoinConferenceUseCase
import com.cws.meeting.core.domain.conference.ObserveConferenceDetailUseCase
import com.cws.meeting.core.model.ConferenceSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConferenceDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeConferenceDetail: ObserveConferenceDetailUseCase,
    private val joinConference: JoinConferenceUseCase,
    private val analyticsHelper: AnalyticsHelper,
) : ViewModel() {

    private val args: ConferenceDetail = savedStateHandle.toRoute()

    private val isJoining = MutableStateFlow(false)
    private val joinedEvents = Channel<ConferenceSession>(Channel.BUFFERED)
    val joined: Flow<ConferenceSession> = joinedEvents.receiveAsFlow()

    val uiState: StateFlow<ConferenceDetailUiState> = combine(
        observeConferenceDetail(args.conferenceId),
        isJoining,
    ) { conference, joining ->
        when (conference) {
            null -> ConferenceDetailUiState.NotFound
            else -> ConferenceDetailUiState.Loaded(conference, isJoining = joining)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConferenceDetailUiState.Loading,
    )

    fun join() {
        if (isJoining.value) return
        viewModelScope.launch {
            isJoining.value = true
            val result = joinConference(args.conferenceId)
            isJoining.value = false
            analyticsHelper.logEvent(
                AnalyticsEvent(
                    type = "conference_join",
                    extras = mapOf(
                        "conference_id" to args.conferenceId,
                        "success" to result.isSuccess.toString(),
                    ),
                ),
            )
            result.getOrNull()?.let { joinedEvents.send(it) }
        }
    }
}
