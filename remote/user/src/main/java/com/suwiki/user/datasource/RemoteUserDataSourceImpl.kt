package com.suwiki.user.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.model.Result
import com.suwiki.model.Token
import com.suwiki.model.User
import com.suwiki.user.api.UserApi
import com.suwiki.user.request.FindIdRequest
import com.suwiki.user.request.FindPasswordRequest
import com.suwiki.user.request.LoginRequest
import com.suwiki.user.request.QuitRequest
import com.suwiki.user.request.ResetPasswordRequest
import com.suwiki.user.response.toModel
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {

    override suspend fun findId(email: String): Result<Boolean> {
        return userApi.findId(FindIdRequest(email)).toResult().map { it.success }
    }

    override suspend fun findPassword(loginId: String, email: String): Result<Boolean> {
        return userApi.findPassword(
            FindPasswordRequest(loginId, email),
        ).toResult().map { it.success }
    }

    override suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Boolean> {
        return userApi.resetPassword(
            ResetPasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword,
            ),
        ).toResult().map { it.success }
    }

    override suspend fun login(loginId: String, password: String): Result<Token> {
        return userApi.login(
            LoginRequest(
                loginId = loginId,
                password = password,
            ),
        ).toResult().map { it.toModel() }
    }

    override suspend fun quit(id: String, password: String): Result<Boolean> {
        return userApi.quit(
            QuitRequest(
                id = id,
                password = password,
            ),
        ).toResult().map { it.success }
    }

    override suspend fun getUserData(): Result<User> {
        return userApi.getUserData().toResult().map { it.toModel() }
    }
}
