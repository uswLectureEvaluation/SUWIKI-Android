package com.suwiki.data.datasource.remote

import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User

interface RemoteUserDataSource {
    suspend fun signUp(
        id: String,
        password: String,
        email: String,
    ): Result<Boolean>

    suspend fun checkId(loginId: String): Result<Boolean>

    suspend fun checkEmail(email: String): Result<Boolean>

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

    fun requestRefresh(refresh: String): Result<Token>

    suspend fun quit(
        id: String,
        password: String,
    ): Result<Boolean>

    suspend fun getUserData(): Result<User>

    suspend fun getSuspensionHistory(): Result<List<Suspension.Ban>>

    suspend fun getBlacklistHistory(): Result<List<Suspension.Block>>
}
