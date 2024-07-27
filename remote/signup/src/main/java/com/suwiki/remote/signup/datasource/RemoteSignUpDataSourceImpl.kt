package com.suwiki.remote.signup.datasource

import com.suwiki.common.model.exception.RequestFailException
import com.suwiki.data.signup.datasource.RemoteSignUpDataSource
import com.suwiki.remote.signup.api.SignUpApi
import com.suwiki.remote.signup.request.CheckEmailRequest
import com.suwiki.remote.signup.request.CheckIdRequest
import com.suwiki.remote.signup.request.SignupRequest
import javax.inject.Inject

class RemoteSignUpDataSourceImpl @Inject constructor(
  private val signUpApi: SignUpApi,
) : RemoteSignUpDataSource {
  override suspend fun signUp(id: String, password: String, email: String) {
    val request = SignupRequest(
      loginId = id,
      password = password,
      email = email,
    )

    signUpApi
      .signUp(
        signupRequest = request,
      )
      .getOrThrow()
      .run { if (!success) throw RequestFailException() }
  }

  override suspend fun checkIdOverlap(loginId: String): Boolean {
    return signUpApi.checkId(CheckIdRequest(loginId)).getOrThrow().overlap
  }

  override suspend fun checkEmailOverlap(email: String): Boolean {
    return signUpApi.checkEmail(CheckEmailRequest(email)).getOrThrow().overlap
  }
}
