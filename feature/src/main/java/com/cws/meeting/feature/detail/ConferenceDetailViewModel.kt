package com.cws.meeting.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cws.meeting.core.domain.conference.ObserveConferenceDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConferenceDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeConferenceDetail: ObserveConferenceDetailUseCase,
) : ViewModel() {

    private val args: ConferenceDetail = savedStateHandle.toRoute()

    val uiState: StateFlow<ConferenceDetailUiState> = observeConferenceDetail(args.conferenceId)
        .map { conference ->
            if (conference == null) ConferenceDetailUiState.NotFound
            else ConferenceDetailUiState.Loaded(conference)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConferenceDetailUiState.Loading,
        )
}
