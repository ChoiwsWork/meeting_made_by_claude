package com.cws.meeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cws.meeting.common.designsystem.theme.MeetingTheme
import com.cws.meeting.core.model.ConferenceSession
import com.cws.meeting.feature.detail.ConferenceDetail
import com.cws.meeting.feature.detail.ConferenceDetailRoute
import com.cws.meeting.feature.home.ConferenceListRoute
import com.cws.meeting.feature.home.Home
import com.cws.meeting.room.ConferenceRoomActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeetingTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Home,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable<Home> {
                        ConferenceListRoute(
                            onConferenceClick = { id ->
                                navController.navigate(ConferenceDetail(conferenceId = id))
                            },
                            onJoinSuccess = ::launchConferenceRoom,
                        )
                    }
                    composable<ConferenceDetail> {
                        ConferenceDetailRoute(
                            onBackClick = { navController.popBackStack() },
                            onJoinSuccess = ::launchConferenceRoom,
                        )
                    }
                }
            }
        }
    }

    private fun launchConferenceRoom(session: ConferenceSession) {
        startActivity(ConferenceRoomActivity.createIntent(this, session))
    }
}
