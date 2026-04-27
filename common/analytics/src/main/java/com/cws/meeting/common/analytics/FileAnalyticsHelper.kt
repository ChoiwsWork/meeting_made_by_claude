package com.cws.meeting.common.analytics

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FileAnalyticsHelper @Inject constructor(
    @ApplicationContext context: Context,
) : AnalyticsHelper {

    private val logsDir: File = File(context.filesDir, LOGS_DIR_NAME).apply { mkdirs() }
    private val timestampFormat = SimpleDateFormat(TIMESTAMP_PATTERN, Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    private val channel = Channel<AnalyticsEvent>(
        capacity = CHANNEL_CAPACITY,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            channel.consumeEach { event ->
                runCatching { append(event) }
            }
        }
    }

    override fun logEvent(event: AnalyticsEvent) {
        channel.trySend(event)
    }

    private fun append(event: AnalyticsEvent) {
        val target = rotateIfNeeded(File(logsDir, ACTIVE_FILE_NAME))
        FileWriter(target, true).use { writer ->
            writer.appendLine(format(event))
        }
    }

    private fun rotateIfNeeded(active: File): File {
        if (active.exists() && active.length() >= MAX_FILE_SIZE_BYTES) {
            val rolled = File(logsDir, "events-${System.currentTimeMillis()}.log")
            active.renameTo(rolled)
        }
        return active
    }

    private fun format(event: AnalyticsEvent): String {
        val timestamp = timestampFormat.format(Date())
        val extras = event.extras.entries.joinToString(",") { (k, v) -> "$k=$v" }
        return "$timestamp|${event.type}|$extras"
    }

    private companion object {
        const val LOGS_DIR_NAME = "analytics-logs"
        const val ACTIVE_FILE_NAME = "events.log"
        const val MAX_FILE_SIZE_BYTES = 1L * 1024 * 1024
        const val CHANNEL_CAPACITY = 256
        const val TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}
