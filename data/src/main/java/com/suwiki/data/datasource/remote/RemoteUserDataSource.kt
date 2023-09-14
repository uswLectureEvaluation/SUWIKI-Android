package com.suwiki.data.datasource.remote

import com.suwiki.model.Result
import com.suwiki.model.Token
import com.suwiki.model.User

interface RemoteUserDataSource {
    suspend fun findId(email: String): Result<Boolean>

    suspend fun findPassword(
        loginId: String,
        email: String,
    ): Result<Boolean>

    suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Boolean>

    suspend fun login(
        loginId: String,
        password: String,
    ): Result<Token>

    suspend fun quit(
        id: String,
        password: String,
    ): Result<Boolean>

    suspend fun getUserData(): Result<User>
}
