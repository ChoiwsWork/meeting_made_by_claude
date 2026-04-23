package com.cws.meeting.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cws.meeting.core.domain.conference.ObserveConferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConferenceListViewModel @Inject constructor(
    observeConferences: ObserveConferencesUseCase,
) : ViewModel() {

    val uiState: StateFlow<ConferenceListUiState> = observeConferences()
        .map { ConferenceListUiState(conferences = it, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConferenceListUiState(),
        )
}
