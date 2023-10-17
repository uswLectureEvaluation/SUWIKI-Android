package com.suwiki.remote.user.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.core.model.Result
import com.suwiki.core.model.Token
import com.suwiki.core.model.User
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

    override suspend fun findId(email: String): com.suwiki.core.model.Result<Boolean> {
        return userApi.findId(FindIdRequest(email)).toResult().map { it.success }
    }

    override suspend fun findPassword(loginId: String, email: String): com.suwiki.core.model.Result<Boolean> {
        return userApi.findPassword(
            FindPasswordRequest(loginId, email),
        ).toResult().map { it.success }
    }

    override suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
    ): com.suwiki.core.model.Result<Boolean> {
        return userApi.resetPassword(
            ResetPasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword,
            ),
        ).toResult().map { it.success }
    }

    override suspend fun login(loginId: String, password: String): com.suwiki.core.model.Result<Token> {
        return userApi.login(
            LoginRequest(
                loginId = loginId,
                password = password,
            ),
        ).toResult().map { it.toModel() }
    }

    override suspend fun quit(id: String, password: String): com.suwiki.core.model.Result<Boolean> {
        return userApi.quit(
            QuitRequest(
                id = id,
                password = password,
            ),
        ).toResult().map { it.success }
    }

    override suspend fun getUserData(): com.suwiki.core.model.Result<User> {
        return userApi.getUserData().toResult().map { it.toModel() }
    }
}
