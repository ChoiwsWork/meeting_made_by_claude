package com.cws.meeting.datasource.di

import com.cws.meeting.datasource.ConferenceDataSource
import com.cws.meeting.datasource.FakeConferenceDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConferenceDataSourceModule {

    @Binds
    abstract fun bindConferenceDataSource(
        impl: FakeConferenceDataSource,
    ): ConferenceDataSource
}
