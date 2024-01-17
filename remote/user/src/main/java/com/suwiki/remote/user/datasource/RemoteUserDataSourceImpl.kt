package com.suwiki.remote.user.datasource

import com.suwiki.core.model.exception.RequestFailException
import com.suwiki.core.model.user.Suspension
import com.suwiki.core.model.user.User
import com.suwiki.data.user.datasource.RemoteUserDataSource
import com.suwiki.remote.user.api.UserApi
import com.suwiki.remote.user.request.QuitRequest
import com.suwiki.remote.user.request.ResetPasswordRequest
import com.suwiki.remote.user.response.toModel
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
  private val userApi: UserApi,
) : RemoteUserDataSource {

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

  override suspend fun getUserInfo(): User {
    return userApi.getUserData().getOrThrow().toModel()
  }

  override suspend fun getBanHistory(): List<Suspension.Ban> {
    return userApi.getBanHistoryData().getOrThrow().map { it.toModel() }
  }
}
