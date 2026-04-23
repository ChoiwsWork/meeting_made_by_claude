package com.cws.meeting.core.domain.user

import com.cws.meeting.core.model.User
import com.cws.meeting.core.service.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(): Flow<User> = repository.observeCurrentUser()
}
