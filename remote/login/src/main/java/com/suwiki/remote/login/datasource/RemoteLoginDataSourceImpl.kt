package com.suwiki.remote.login.datasource

import com.suwiki.common.model.exception.RequestFailException
import com.suwiki.common.model.user.Token
import com.suwiki.data.login.datasource.RemoteLoginDataSource
import com.suwiki.remote.login.api.LoginApi
import com.suwiki.remote.login.request.FindIdRequest
import com.suwiki.remote.login.request.FindPasswordRequest
import com.suwiki.remote.login.request.LoginRequest
import com.suwiki.remote.login.response.toModel
import javax.inject.Inject

class RemoteLoginDataSourceImpl @Inject constructor(
  private val loginApi: LoginApi,
) : RemoteLoginDataSource {

  override suspend fun findId(email: String) {
    loginApi
      .findId(FindIdRequest(email))
      .getOrThrow()
      .run {
        if (!success) throw RequestFailException()
      }
  }

  override suspend fun findPassword(loginId: String, email: String) {
    loginApi
      .findPassword(
        FindPasswordRequest(loginId, email),
      )
      .getOrThrow()
      .run {
        if (!success) throw RequestFailException()
      }
  }

  override suspend fun login(loginId: String, password: String): Token {
    return loginApi.login(
      LoginRequest(
        loginId = loginId,
        password = password,
      ),
    ).getOrThrow().toModel()
  }
}
