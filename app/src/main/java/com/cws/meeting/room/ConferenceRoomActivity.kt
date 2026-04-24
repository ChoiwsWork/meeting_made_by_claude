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
import com.cws.meeting.feature.room.ConferenceRoomScreen

class ConferenceRoomActivity : ComponentActivity() {

    private var isInPipMode by mutableStateOf(false)
    private var conferenceId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        conferenceId = intent.getStringExtra(EXTRA_CONFERENCE_ID).orEmpty()
        setContent {
            MeetingTheme {
                ConferenceRoomScreen(
                    conferenceId = conferenceId,
                    isInPipMode = isInPipMode,
                )
            }
        }
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

        fun createIntent(context: Context, conferenceId: String): Intent =
            Intent(context, ConferenceRoomActivity::class.java).apply {
                putExtra(EXTRA_CONFERENCE_ID, conferenceId)
            }
    }
}
