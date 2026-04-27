package com.cws.meeting.feature.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cws.meeting.common.designsystem.theme.MeetingTheme
import com.cws.meeting.core.model.ConferenceInfo
import com.cws.meeting.core.model.ConferenceMode

@Composable
internal fun ConferenceInfoChip(
    modifier: Modifier = Modifier,
    viewModel: ConferenceInfoChipViewModel = hiltViewModel(),
) {
    val info by viewModel.info.collectAsStateWithLifecycle()
    ConferenceInfoChipContent(info = info, modifier = modifier)
}

@Composable
internal fun ConferenceInfoChipContent(
    info: ConferenceInfo?,
    modifier: Modifier = Modifier,
) {
    if (info == null) return
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = info.agenda,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ModeBadge(mode = info.mode)
                Text(
                    text = "Room ${info.roomNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ModeBadge(mode: ConferenceMode) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Text(
            text = when (mode) {
                ConferenceMode.SEMINAR -> "Seminar"
                ConferenceMode.DISCUSSION -> "Discussion"
            },
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun ConferenceInfoChipPreview() {
    MeetingTheme(dynamicColor = false) {
        Box(Modifier.padding(16.dp)) {
            ConferenceInfoChipContent(
                info = ConferenceInfo(
                    agenda = "Quarterly planning: priorities, staffing, and roadmap review",
                    roomNumber = "conf-1",
                    mode = ConferenceMode.DISCUSSION,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
