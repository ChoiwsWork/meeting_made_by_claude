package com.cws.meeting.core.service.user

import com.cws.meeting.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeCurrentUser(): Flow<User>
}
