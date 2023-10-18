package com.suwiki.domain.repository

import com.suwiki.domain.model.LoggedInUser
import kotlinx.coroutines.flow.Flow
import com.suwiki.domain.model.Result

interface UserRepository {
    val isLoggedIn: Flow<Boolean>
    val userInfo: Flow<Result<LoggedInUser>>
}
