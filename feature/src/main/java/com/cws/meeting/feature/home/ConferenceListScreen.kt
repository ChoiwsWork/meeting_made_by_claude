@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cws.meeting.common.designsystem.theme.MeetingTheme
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun ConferenceListRoute(
    onConferenceClick: (String) -> Unit,
    viewModel: ConferenceListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ConferenceListScreen(state = uiState, onConferenceClick = onConferenceClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConferenceListScreen(
    state: ConferenceListUiState,
    onConferenceClick: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Conferences") }) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        when {
            state.isLoading -> LoadingIndicator(innerPadding)
            state.conferences.isEmpty() -> EmptyState(innerPadding)
            else -> ConferenceList(state.conferences, innerPadding, onConferenceClick)
        }
    }
}

@Composable
private fun LoadingIndicator(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        Text("No conferences scheduled")
    }
}

@Composable
private fun ConferenceList(
    conferences: List<ConferenceSummary>,
    padding: PaddingValues,
    onConferenceClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = padding.calculateTopPadding() + 8.dp,
            bottom = padding.calculateBottomPadding() + 8.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = conferences, key = { it.id }) { conference ->
            ConferenceCard(
                conference = conference,
                onClick = { onConferenceClick(conference.id) },
            )
        }
    }
}

@Composable
private fun ConferenceCard(
    conference: ConferenceSummary,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = conference.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "Host: ${conference.hostDisplayName}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = conference.scheduledAt.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private val sampleSummaries = listOf(
    ConferenceSummary(
        id = "conf-1",
        title = "Weekly sync",
        scheduledAt = Instant.parse("2026-04-23T10:00:00Z"),
        hostDisplayName = "Wooseok",
    ),
    ConferenceSummary(
        id = "conf-2",
        title = "Architecture review",
        scheduledAt = Instant.parse("2026-04-23T13:00:00Z"),
        hostDisplayName = "Jihye",
    ),
    ConferenceSummary(
        id = "conf-3",
        title = "Retrospective",
        scheduledAt = Instant.parse("2026-04-24T09:00:00Z"),
        hostDisplayName = "Minsu",
    ),
)

@PreviewLightDark
@Composable
private fun ConferenceListScreenPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceListScreen(
            state = ConferenceListUiState(
                conferences = sampleSummaries,
                isLoading = false,
            ),
            onConferenceClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun ConferenceListScreenEmptyPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceListScreen(
            state = ConferenceListUiState(
                conferences = emptyList(),
                isLoading = false,
            ),
            onConferenceClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun ConferenceCardPreview() {
    MeetingTheme(dynamicColor = false) {
        Box(modifier = Modifier.padding(16.dp)) {
            ConferenceCard(
                conference = sampleSummaries.first(),
                onClick = {},
            )
        }
    }
}
