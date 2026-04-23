package com.cws.meeting.datasource

import com.cws.meeting.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserDataSource @Inject constructor() : UserDataSource {

    private val state = MutableStateFlow(CurrentUser)

    override fun observeCurrentUser(): Flow<User> = state.asStateFlow()

    internal companion object {
        val CurrentUser = User(
            id = "user-wooseok",
            displayName = "Wooseok",
            email = "wooseok@example.com",
        )

        val Jihye = User(
            id = "user-jihye",
            displayName = "Jihye",
            email = null,
        )

        val Minsu = User(
            id = "user-minsu",
            displayName = "Minsu",
            email = null,
        )

        val Haeun = User(
            id = "user-haeun",
            displayName = "Haeun",
            email = "haeun@example.com",
        )

        val Dowon = User(
            id = "user-dowon",
            displayName = "Dowon",
            email = null,
        )
    }
}
