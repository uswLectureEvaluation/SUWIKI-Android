package com.suwiki.remote.user.datasource

import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User
import com.suwiki.data.user.datasource.RemoteUserDataSource
import com.suwiki.remote.user.api.UserApi
import com.suwiki.remote.user.request.FindIdRequest
import com.suwiki.remote.user.request.FindPasswordRequest
import com.suwiki.remote.user.request.LoginRequest
import com.suwiki.remote.user.request.QuitRequest
import com.suwiki.remote.user.request.ResetPasswordRequest
import com.suwiki.remote.user.response.toModel
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {

    override suspend fun findId(email: String): Boolean {
        return userApi.findId(FindIdRequest(email)).getOrThrow().success
    }

    override suspend fun findPassword(loginId: String, email: String): Boolean {
        return userApi.findPassword(
            FindPasswordRequest(loginId, email),
        ).getOrThrow().success
    }

    override suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): Boolean {
        return userApi.resetPassword(
            ResetPasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword,
            ),
        ).getOrThrow().success
    }

    override suspend fun login(loginId: String, password: String): Token {
        return userApi.login(
            LoginRequest(
                loginId = loginId,
                password = password,
            ),
        ).getOrThrow().toModel()
    }

    override suspend fun quit(id: String, password: String): Boolean {
        return userApi.quit(
            QuitRequest(
                id = id,
                password = password,
            ),
        ).getOrThrow().success
    }

    override suspend fun getUserData(): User {
        return userApi.getUserData().getOrThrow().toModel()
    }
}
