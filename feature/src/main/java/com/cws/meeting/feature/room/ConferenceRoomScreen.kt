package com.cws.meeting.feature.room

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cws.meeting.common.designsystem.theme.MeetingTheme

@Composable
fun ConferenceRoomScreen(
    conferenceId: String,
    isInPipMode: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = if (isInPipMode) "PIP: $conferenceId" else "Conference Room: $conferenceId",
                style = if (isInPipMode) MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ConferenceRoomScreenPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceRoomScreen(conferenceId = "conf-1", isInPipMode = false)
    }
}

@PreviewLightDark
@Composable
private fun ConferenceRoomScreenPipPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceRoomScreen(conferenceId = "conf-1", isInPipMode = true)
    }
}
