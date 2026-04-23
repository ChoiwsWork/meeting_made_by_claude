package com.cws.meeting.core.service.conference.di

import com.cws.meeting.core.service.conference.ConferenceRepository
import com.cws.meeting.core.service.conference.DefaultConferenceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConferenceRepositoryModule {

    @Binds
    abstract fun bindConferenceRepository(
        impl: DefaultConferenceRepository,
    ): ConferenceRepository
}
