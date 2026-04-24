package com.cws.meeting.core.service.room

import com.cws.meeting.core.model.ChatMessage
import com.cws.meeting.core.model.ConferenceSession
import kotlinx.coroutines.flow.StateFlow

interface ChatService {
    val messages: StateFlow<List<ChatMessage>>
    suspend fun send(content: String)
    fun start(session: ConferenceSession)
    fun stop()
}
