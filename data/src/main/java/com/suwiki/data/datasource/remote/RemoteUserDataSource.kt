package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Token
import com.suwiki.core.model.User

interface RemoteUserDataSource {
    suspend fun findId(email: String): Boolean

    suspend fun findPassword(
        loginId: String,
        email: String,
    ): Boolean

    suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): Boolean

    suspend fun login(
        loginId: String,
        password: String,
    ): Token

    suspend fun quit(
        id: String,
        password: String,
    ): Boolean

    suspend fun getUserData(): User
}
