package com.suwiki.domain.repository

import com.suwiki.domain.model.LoggedInUser
import com.suwiki.core.model.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val isLoggedIn: Flow<Boolean>
    val userInfo: Flow<com.suwiki.core.model.Result<LoggedInUser>>
}
