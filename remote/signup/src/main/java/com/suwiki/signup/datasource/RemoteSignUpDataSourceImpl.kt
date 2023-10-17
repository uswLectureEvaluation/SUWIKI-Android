package com.suwiki.signup.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteSignUpDataSource
import com.suwiki.core.model.Result
import com.suwiki.signup.api.SignUpApi
import com.suwiki.signup.request.CheckEmailRequest
import com.suwiki.signup.request.CheckIdRequest
import com.suwiki.signup.request.SignupRequest
import javax.inject.Inject

class RemoteSignUpDataSourceImpl @Inject constructor(
    private val signUpApi: SignUpApi,
) : RemoteSignUpDataSource {
    override suspend fun signUp(id: String, password: String, email: String): com.suwiki.core.model.Result<Boolean> {
        val request = SignupRequest(
            id = id,
            password = password,
            email = email,
        )

        return signUpApi.signUp(
            signupRequest = request,
        ).toResult().map { it.success }
    }

    override suspend fun checkIdOverlap(loginId: String): com.suwiki.core.model.Result<Boolean> {
        return signUpApi.checkId(CheckIdRequest(loginId)).toResult().map {
            it.overlap
        }
    }

    override suspend fun checkEmailOverlap(email: String): com.suwiki.core.model.Result<Boolean> {
        return signUpApi.checkEmail(CheckEmailRequest(email)).toResult().map { it.overlap }
    }
}
