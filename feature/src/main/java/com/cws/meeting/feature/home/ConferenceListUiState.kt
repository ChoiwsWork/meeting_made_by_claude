package com.cws.meeting.feature.home

import com.cws.meeting.core.model.Conference

data class ConferenceListUiState(
    val conferences: List<Conference> = emptyList(),
    val isLoading: Boolean = true,
)
