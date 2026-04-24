@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cws.meeting.common.designsystem.theme.MeetingTheme
import com.cws.meeting.core.model.Conference
import com.cws.meeting.core.model.ConferenceSession
import com.cws.meeting.core.model.User
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun ConferenceDetailRoute(
    onBackClick: () -> Unit,
    onJoinSuccess: (ConferenceSession) -> Unit,
    viewModel: ConferenceDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.joined.collect(onJoinSuccess)
    }
    ConferenceDetailScreen(
        state = uiState,
        onBackClick = onBackClick,
        onJoinClick = viewModel::join,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConferenceDetailScreen(
    state: ConferenceDetailUiState,
    onBackClick: () -> Unit,
    onJoinClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conference") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
        bottomBar = {
            if (state is ConferenceDetailUiState.Loaded) {
                JoinBar(isJoining = state.isJoining, onJoinClick = onJoinClick)
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        when (state) {
            ConferenceDetailUiState.Loading -> CenteredBox(innerPadding) {
                CircularProgressIndicator()
            }
            ConferenceDetailUiState.NotFound -> CenteredBox(innerPadding) {
                Text("Conference not found")
            }
            is ConferenceDetailUiState.Loaded -> ConferenceDetailContent(
                conference = state.conference,
                padding = innerPadding,
            )
        }
    }
}

@Composable
private fun JoinBar(
    isJoining: Boolean,
    onJoinClick: () -> Unit,
) {
    Surface(tonalElevation = 3.dp) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Button(
                onClick = onJoinClick,
                enabled = !isJoining,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (isJoining) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Text("Joining...")
                    }
                } else {
                    Text("Join Meeting")
                }
            }
        }
    }
}

@Composable
private fun CenteredBox(
    padding: PaddingValues,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun ConferenceDetailContent(
    conference: Conference,
    padding: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = padding.calculateTopPadding() + 16.dp,
            bottom = padding.calculateBottomPadding() + 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            HeaderSection(conference = conference)
        }
        item {
            HorizontalDivider()
        }
        item {
            Text(
                text = "Attendees (${conference.attendees.size})",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
            )
        }
        items(items = conference.attendees, key = { it.id }) { attendee ->
            AttendeeRow(user = attendee, isHost = attendee.id == conference.host.id)
        }
    }
}

@Composable
private fun HeaderSection(conference: Conference) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = conference.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = conference.scheduledAt.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "Host: ${conference.host.displayName}",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun AttendeeRow(user: User, isHost: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Avatar(initial = user.displayName.firstOrNull()?.toString().orEmpty())
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if (isHost) "${user.displayName} (Host)" else user.displayName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isHost) FontWeight.SemiBold else FontWeight.Normal,
            )
            user.email?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun Avatar(initial: String) {
    Surface(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = initial,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

private val sampleHost = User(id = "u1", displayName = "Wooseok", email = "wooseok@example.com")
private val sampleConference = Conference(
    id = "conf-1",
    title = "Weekly sync",
    scheduledAt = Instant.parse("2026-04-23T10:00:00Z"),
    host = sampleHost,
    attendees = listOf(
        sampleHost,
        User(id = "u2", displayName = "Jihye", email = null),
        User(id = "u3", displayName = "Minsu", email = null),
        User(id = "u4", displayName = "Haeun", email = "haeun@example.com"),
    ),
)

@PreviewLightDark
@Composable
private fun ConferenceDetailScreenPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceDetailScreen(
            state = ConferenceDetailUiState.Loaded(sampleConference),
            onBackClick = {},
            onJoinClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun ConferenceDetailScreenJoiningPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceDetailScreen(
            state = ConferenceDetailUiState.Loaded(sampleConference, isJoining = true),
            onBackClick = {},
            onJoinClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun ConferenceDetailScreenNotFoundPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceDetailScreen(
            state = ConferenceDetailUiState.NotFound,
            onBackClick = {},
            onJoinClick = {},
        )
    }
}
