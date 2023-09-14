package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.model.Result
import com.suwiki.model.Token
import com.suwiki.model.User
import com.suwiki.remote.api.UserApi
import com.suwiki.remote.request.user.FindIdRequest
import com.suwiki.remote.request.user.FindPasswordRequest
import com.suwiki.remote.request.user.LoginRequest
import com.suwiki.remote.request.user.QuitRequest
import com.suwiki.remote.request.user.ResetPasswordRequest
import com.suwiki.remote.response.user.toModel
import com.suwiki.remote.toResult
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
