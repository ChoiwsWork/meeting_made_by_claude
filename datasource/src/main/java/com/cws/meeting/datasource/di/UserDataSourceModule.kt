package com.cws.meeting.datasource.di

import com.cws.meeting.datasource.FakeUserDataSource
import com.cws.meeting.datasource.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserDataSourceModule {

    @Binds
    abstract fun bindUserDataSource(
        impl: FakeUserDataSource,
    ): UserDataSource
}
