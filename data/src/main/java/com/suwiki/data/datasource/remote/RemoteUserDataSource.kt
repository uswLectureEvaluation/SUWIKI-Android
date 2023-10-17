package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result
import com.suwiki.core.model.Token
import com.suwiki.core.model.User

interface RemoteUserDataSource {
    suspend fun findId(email: String): com.suwiki.core.model.Result<Boolean>

    suspend fun findPassword(
        loginId: String,
        email: String,
    ): com.suwiki.core.model.Result<Boolean>

    suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): com.suwiki.core.model.Result<Boolean>

    suspend fun login(
        loginId: String,
        password: String,
    ): com.suwiki.core.model.Result<Token>

    suspend fun quit(
        id: String,
        password: String,
    ): com.suwiki.core.model.Result<Boolean>

    suspend fun getUserData(): com.suwiki.core.model.Result<User>
}
