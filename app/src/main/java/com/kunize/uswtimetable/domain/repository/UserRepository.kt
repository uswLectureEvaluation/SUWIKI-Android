package com.kunize.uswtimetable.domain.repository

import com.kunize.uswtimetable.dataclass.LoggedInUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val isLoggedIn: Flow<Boolean>
    val userInfo: Flow<LoggedInUser?>

    suspend fun login()
    suspend fun logout()
}
