package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User
import com.suwiki.remote.api.UserApi
import com.suwiki.remote.request.CheckEmailRequest
import com.suwiki.remote.request.CheckIdRequest
import com.suwiki.remote.request.FindIdRequest
import com.suwiki.remote.request.FindPasswordRequest
import com.suwiki.remote.request.LoginRequest
import com.suwiki.remote.request.QuitRequest
import com.suwiki.remote.request.ResetPasswordRequest
import com.suwiki.remote.request.SignupRequest
import com.suwiki.remote.response.evaluation.toModel
import com.suwiki.remote.response.user.toModel
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
        return userApi.checkId(CheckIdRequest(loginId)).toResult().map {
            it.overlap
        }
    }

    override suspend fun checkEmail(email: String): Result<Boolean> {
        return userApi.checkEmail(CheckEmailRequest(email)).toResult().map { it.overlap }
    }

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

    override fun requestRefresh(refresh: String): Result<Token> {
        return userApi.requestRefresh(refresh).toResult().map { it.toModel() }
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
        return userApi.getSuspensionHistory().toResult().map { result -> result.map { it.toModel() } }
    }

    override suspend fun getBlacklistHistory(): Result<List<Suspension.Block>> {
        return userApi.getBlacklistHistory().toResult().map { result -> result.map { it.toModel() } }
    }
}
