package com.cws.meeting.feature.home

data class ConferenceListUiState(
    val conferences: List<ConferenceSummary> = emptyList(),
    val isLoading: Boolean = true,
)
