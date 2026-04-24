package com.cws.meeting.feature.room

import androidx.lifecycle.ViewModel
import com.cws.meeting.core.model.ConferenceInfo
import com.cws.meeting.core.service.room.ConferenceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ConferenceRoomViewModel @Inject constructor(
    conferenceService: ConferenceService,
) : ViewModel() {

    val info: StateFlow<ConferenceInfo?> = conferenceService.info
}
