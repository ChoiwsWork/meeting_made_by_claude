package com.cws.meeting.feature.detail

import com.cws.meeting.core.model.Conference

sealed interface ConferenceDetailUiState {
    data object Loading : ConferenceDetailUiState
    data object NotFound : ConferenceDetailUiState
    data class Loaded(val conference: Conference) : ConferenceDetailUiState
}
