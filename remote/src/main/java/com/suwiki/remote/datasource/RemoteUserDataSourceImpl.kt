package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User
import com.suwiki.remote.api.AuthApi
import com.suwiki.remote.api.UserApi
import com.suwiki.remote.request.user.CheckEmailRequest
import com.suwiki.remote.request.user.CheckIdRequest
import com.suwiki.remote.request.user.FindIdRequest
import com.suwiki.remote.request.user.FindPasswordRequest
import com.suwiki.remote.request.user.LoginRequest
import com.suwiki.remote.request.user.QuitRequest
import com.suwiki.remote.request.user.ResetPasswordRequest
import com.suwiki.remote.request.user.SignupRequest
import com.suwiki.remote.response.user.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val authApi: AuthApi,
) : RemoteUserDataSource {
    override suspend fun signUp(id: String, password: String, email: String): Result<Boolean> {
        val request = SignupRequest(
            id = id,
            password = password,
            email = email,
        )

        return authApi.signUp(
            signupRequest = request,
        ).toResult().map { it.success }
    }

    override suspend fun checkId(loginId: String): Result<Boolean> {
        return authApi.checkId(CheckIdRequest(loginId)).toResult().map {
            it.overlap
        }
    }

    override suspend fun checkEmail(email: String): Result<Boolean> {
        return authApi.checkEmail(CheckEmailRequest(email)).toResult().map { it.overlap }
    }

    override suspend fun findId(email: String): Result<Boolean> {
        return authApi.findId(FindIdRequest(email)).toResult().map { it.success }
    }

    override suspend fun findPassword(loginId: String, email: String): Result<Boolean> {
        return authApi.findPassword(
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
        return authApi.login(
            LoginRequest(
                loginId = loginId,
                password = password,
            ),
        ).toResult().map { it.toModel() }
    }

    override fun requestRefresh(refresh: String): Result<Token> {
        return authApi.requestRefresh(refresh).toResult().map { it.toModel() }
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

    override suspend fun getSuspensionHistory(): Result<List<Suspension.Ban>> {
        return userApi.getSuspensionHistory().toResult()
            .map { result -> result.map { it.toModel() } }
    }

    override suspend fun getBlacklistHistory(): Result<List<Suspension.Block>> {
        return userApi.getBlacklistHistory().toResult()
            .map { result -> result.map { it.toModel() } }
    }
}
