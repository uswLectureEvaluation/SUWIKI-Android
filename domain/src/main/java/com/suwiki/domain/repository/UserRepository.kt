package com.suwiki.domain.repository

import com.suwiki.domain.model.LoggedInUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val isLoggedIn: Flow<Boolean>
    val userInfo: Flow<LoggedInUser?>

    suspend fun setUserInfo(): Boolean
}
