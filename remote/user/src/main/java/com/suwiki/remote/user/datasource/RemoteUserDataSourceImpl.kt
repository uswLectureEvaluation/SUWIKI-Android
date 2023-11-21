package com.suwiki.remote.user.datasource

import com.suwiki.core.model.exception.RequestFailException
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

  override suspend fun findId(email: String) {
    userApi
      .findId(FindIdRequest(email))
      .getOrThrow()
      .run {
        if (!success) throw RequestFailException()
      }
  }

  override suspend fun findPassword(loginId: String, email: String) {
    userApi
      .findPassword(
        FindPasswordRequest(loginId, email),
      )
      .getOrThrow()
      .run {
        if (!success) throw RequestFailException()
      }
  }

  override suspend fun resetPassword(
    currentPassword: String,
    newPassword: String,
  ) {
    userApi.resetPassword(
      ResetPasswordRequest(
        currentPassword = currentPassword,
        newPassword = newPassword,
      ),
    ).getOrThrow().run {
      if (!success) throw RequestFailException()
    }
  }

  override suspend fun login(loginId: String, password: String): Token {
    return userApi.login(
      LoginRequest(
        loginId = loginId,
        password = password,
      ),
    ).getOrThrow().toModel()
  }

  override suspend fun quit(id: String, password: String) {
    userApi.quit(
      QuitRequest(
        id = id,
        password = password,
      ),
    )
      .getOrThrow()
      .run {
        if (!success) throw RequestFailException()
      }
  }

  override suspend fun getUserData(): User {
    return userApi.getUserData().getOrThrow().toModel()
  }
}
