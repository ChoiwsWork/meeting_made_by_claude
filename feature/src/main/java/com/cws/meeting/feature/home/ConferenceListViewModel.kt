package com.cws.meeting.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cws.meeting.core.domain.conference.JoinConferenceUseCase
import com.cws.meeting.core.domain.conference.ObserveConferencesUseCase
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
class ConferenceListViewModel @Inject constructor(
    observeConferences: ObserveConferencesUseCase,
    private val joinConference: JoinConferenceUseCase,
) : ViewModel() {

    private val joiningId = MutableStateFlow<String?>(null)
    private val joinedEvents = Channel<ConferenceSession>(Channel.BUFFERED)
    val joined: Flow<ConferenceSession> = joinedEvents.receiveAsFlow()

    val uiState: StateFlow<ConferenceListUiState> = combine(
        observeConferences(),
        joiningId,
    ) { conferences, joining ->
        ConferenceListUiState(
            conferences = conferences.map { it.toSummary() },
            isLoading = false,
            joiningId = joining,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConferenceListUiState(),
    )

    fun join(conferenceId: String) {
        if (joiningId.value != null) return
        viewModelScope.launch {
            joiningId.value = conferenceId
            val result = joinConference(conferenceId)
            joiningId.value = null
            result.getOrNull()?.let { joinedEvents.send(it) }
        }
    }
}
