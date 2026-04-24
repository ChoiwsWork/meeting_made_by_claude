package com.cws.meeting.room

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cws.meeting.common.designsystem.theme.MeetingTheme
import com.cws.meeting.core.model.ConferenceSession
import com.cws.meeting.core.service.room.ConferenceSessionController
import com.cws.meeting.feature.room.ConferenceRoomScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConferenceRoomActivity : ComponentActivity() {

    @Inject lateinit var sessionController: ConferenceSessionController

    private var isInPipMode by mutableStateOf(false)
    private lateinit var session: ConferenceSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        session = ConferenceSession(
            sessionId = intent.getStringExtra(EXTRA_SESSION_ID).orEmpty(),
            conferenceId = intent.getStringExtra(EXTRA_CONFERENCE_ID).orEmpty(),
        )
        sessionController.start(session)

        setContent {
            MeetingTheme {
                ConferenceRoomScreen(
                    conferenceId = session.conferenceId,
                    isInPipMode = isInPipMode,
                )
            }
        }
    }

    override fun onDestroy() {
        sessionController.stop()
        super.onDestroy()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPipIfSupported()
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration,
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        isInPipMode = isInPictureInPictureMode
    }

    private fun enterPipIfSupported() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) return
        val params = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(16, 9))
            .build()
        enterPictureInPictureMode(params)
    }

    companion object {
        private const val EXTRA_CONFERENCE_ID = "conference_id"
        private const val EXTRA_SESSION_ID = "session_id"

        fun createIntent(context: Context, session: ConferenceSession): Intent =
            Intent(context, ConferenceRoomActivity::class.java).apply {
                putExtra(EXTRA_CONFERENCE_ID, session.conferenceId)
                putExtra(EXTRA_SESSION_ID, session.sessionId)
            }
    }
}
