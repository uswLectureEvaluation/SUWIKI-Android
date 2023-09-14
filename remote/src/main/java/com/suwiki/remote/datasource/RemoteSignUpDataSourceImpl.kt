package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteSignUpDataSource
import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User
import com.suwiki.remote.api.AuthApi
import com.suwiki.remote.api.SignUpApi
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

class RemoteSignUpDataSourceImpl @Inject constructor(
    private val signUpApi: SignUpApi
) : RemoteSignUpDataSource {
    override suspend fun signUp(id: String, password: String, email: String): Result<Boolean> {
        val request = SignupRequest(
            id = id,
            password = password,
            email = email,
        )

        return signUpApi.signUp(
            signupRequest = request,
        ).toResult().map { it.success }
    }

    override suspend fun checkIdOverlap(loginId: String): Result<Boolean> {
        return signUpApi.checkId(CheckIdRequest(loginId)).toResult().map {
            it.overlap
        }
    }

    override suspend fun checkEmailOverlap(email: String): Result<Boolean> {
        return signUpApi.checkEmail(CheckEmailRequest(email)).toResult().map { it.overlap }
    }
}
