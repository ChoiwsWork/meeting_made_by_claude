package com.cws.meeting.datasource

import com.cws.meeting.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun observeCurrentUser(): Flow<User>
}
