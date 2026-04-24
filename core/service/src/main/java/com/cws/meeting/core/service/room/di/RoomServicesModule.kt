package com.cws.meeting.core.service.room.di

import com.cws.meeting.core.service.room.ChatService
import com.cws.meeting.core.service.room.ConferenceService
import com.cws.meeting.core.service.room.DefaultChatService
import com.cws.meeting.core.service.room.DefaultConferenceService
import com.cws.meeting.core.service.room.DefaultParticipantService
import com.cws.meeting.core.service.room.ParticipantService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RoomServicesModule {

    @Binds
    abstract fun bindConferenceService(impl: DefaultConferenceService): ConferenceService

    @Binds
    abstract fun bindParticipantService(impl: DefaultParticipantService): ParticipantService

    @Binds
    abstract fun bindChatService(impl: DefaultChatService): ChatService
}
