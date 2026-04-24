@file:OptIn(ExperimentalTime::class)

package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ChatMessage
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Singleton
class DefaultChatService @Inject constructor() : ChatService {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    override val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    override suspend fun send(content: String) {
        _messages.update { current ->
            current + ChatMessage(
                id = "msg-${current.size}",
                senderId = LOCAL_SENDER_ID,
                content = content,
                sentAt = Clock.System.now(),
            )
        }
    }

    override fun start(session: ConferenceSession) {
        _messages.value = emptyList()
    }

    override fun stop() {
        _messages.value = emptyList()
    }

    private companion object {
        const val LOCAL_SENDER_ID = "me"
    }
}
