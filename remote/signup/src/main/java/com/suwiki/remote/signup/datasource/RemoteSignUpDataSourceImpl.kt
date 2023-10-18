package com.suwiki.remote.signup.datasource

import com.suwiki.data.datasource.remote.RemoteSignUpDataSource
import com.suwiki.remote.signup.api.SignUpApi
import com.suwiki.remote.signup.request.CheckEmailRequest
import com.suwiki.remote.signup.request.CheckIdRequest
import com.suwiki.remote.signup.request.SignupRequest
import javax.inject.Inject

class RemoteSignUpDataSourceImpl @Inject constructor(
    private val signUpApi: SignUpApi,
) : RemoteSignUpDataSource {
    override suspend fun signUp(id: String, password: String, email: String): Boolean {
        val request = SignupRequest(
            id = id,
            password = password,
            email = email,
        )

        return signUpApi.signUp(
            signupRequest = request,
        ).getOrThrow().success
    }

    override suspend fun checkIdOverlap(loginId: String): Boolean {
        return signUpApi.checkId(CheckIdRequest(loginId)).getOrThrow().overlap
    }

    override suspend fun checkEmailOverlap(email: String): Boolean {
        return signUpApi.checkEmail(CheckEmailRequest(email)).getOrThrow().overlap
    }
}
