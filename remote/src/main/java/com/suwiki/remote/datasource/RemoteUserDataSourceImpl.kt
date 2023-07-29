package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User
import com.suwiki.remote.api.UserApi
import com.suwiki.remote.request.SignupRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {
    override suspend fun signUp(id: String, password: String, email: String): Result<Boolean> {
        val request = SignupRequest(
            id = id,
            password = password,
            email = email,
        )

        return userApi.signUp(
            signupRequest = request,
        ).toResult().map { it.success }
    }

    override suspend fun checkId(loginId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmail(email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findId(email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findPassword(loginId: String, email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun login(loginId: String, password: String): Result<Token> {
        TODO("Not yet implemented")
    }

    override fun requestRefresh(refresh: String): Result<Token> {
        TODO("Not yet implemented")
    }

    override suspend fun quit(id: String, password: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getSuspensionHistory(): Result<List<Suspension.Ban>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBlacklistHistory(): Result<List<Suspension.Block>> {
        TODO("Not yet implemented")
    }
}
