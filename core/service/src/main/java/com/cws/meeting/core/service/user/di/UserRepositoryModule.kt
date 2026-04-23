package com.cws.meeting.core.service.user.di

import com.cws.meeting.core.service.user.DefaultUserRepository
import com.cws.meeting.core.service.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserRepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        impl: DefaultUserRepository,
    ): UserRepository
}
