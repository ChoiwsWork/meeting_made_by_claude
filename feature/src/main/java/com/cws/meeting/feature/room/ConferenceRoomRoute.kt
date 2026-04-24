package com.cws.meeting.feature.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cws.meeting.common.designsystem.theme.MeetingTheme
import com.cws.meeting.core.model.ConferenceInfo
import com.cws.meeting.core.model.ConferenceMode

@Composable
fun ConferenceRoomRoute(
    isInPipMode: Boolean,
    onLeaveClick: () -> Unit,
    viewModel: ConferenceRoomViewModel = hiltViewModel(),
) {
    val info by viewModel.info.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = RoomHome,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<RoomHome> {
                ConferenceRoomHomeScreen(
                    info = info,
                    onLeaveClick = onLeaveClick,
                    onChatClick = { navController.navigate(RoomChat) },
                )
            }
            composable<RoomChat> {
                ChatRoute(onBackClick = { navController.popBackStack() })
            }
        }
        if (isInPipMode) {
            PipOverlay(info = info, modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConferenceRoomHomeScreen(
    info: ConferenceInfo?,
    onLeaveClick: () -> Unit,
    onChatClick: () -> Unit,
) {
    var showParticipants by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = info?.roomNumber?.let { "Room $it" } ?: "Room")
                },
                actions = {
                    IconButton(onClick = onLeaveClick) {
                        Icon(
                            imageVector = Icons.Filled.CallEnd,
                            contentDescription = "Leave",
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(),
            )
        },
        bottomBar = {
            ConferenceMenuBar(
                onParticipantsClick = { showParticipants = true },
                onChatClick = onChatClick,
                onSettingsClick = { showSettings = true },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Main area",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    if (showParticipants) {
        ParticipantsBottomSheet(onDismiss = { showParticipants = false })
    }
    if (showSettings) {
        ConferenceSettingsBottomSheet(onDismiss = { showSettings = false })
    }
}

@Composable
private fun ConferenceMenuBar(
    onParticipantsClick: () -> Unit,
    onChatClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Surface(tonalElevation = 3.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MenuIconButton(
                icon = Icons.Filled.People,
                label = "Participants",
                onClick = onParticipantsClick,
            )
            MenuIconButton(
                icon = Icons.AutoMirrored.Filled.Chat,
                label = "Chat",
                onClick = onChatClick,
            )
            MenuIconButton(
                icon = Icons.Filled.Settings,
                label = "Settings",
                onClick = onSettingsClick,
            )
        }
    }
}

@Composable
private fun MenuIconButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = label)
    }
}

@Composable
private fun PipOverlay(
    info: ConferenceInfo?,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = info?.roomNumber?.let { "PIP: $it" } ?: "PIP",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ConferenceRoomHomeScreenPreview() {
    MeetingTheme(dynamicColor = false) {
        ConferenceRoomHomeScreen(
            info = ConferenceInfo(
                agenda = "Weekly sync agenda",
                roomNumber = "conf-1",
                mode = ConferenceMode.DISCUSSION,
            ),
            onLeaveClick = {},
            onChatClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun PipOverlayPreview() {
    MeetingTheme(dynamicColor = false) {
        PipOverlay(
            info = ConferenceInfo(
                agenda = "Weekly sync agenda",
                roomNumber = "conf-1",
                mode = ConferenceMode.DISCUSSION,
            ),
        )
    }
}
