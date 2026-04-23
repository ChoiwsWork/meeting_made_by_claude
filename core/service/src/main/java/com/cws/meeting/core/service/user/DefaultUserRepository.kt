package com.cws.meeting.core.service.user

import com.cws.meeting.core.model.User
import com.cws.meeting.datasource.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserRepository @Inject constructor(
    private val dataSource: UserDataSource,
) : UserRepository {

    override fun observeCurrentUser(): Flow<User> = dataSource.observeCurrentUser()
}
