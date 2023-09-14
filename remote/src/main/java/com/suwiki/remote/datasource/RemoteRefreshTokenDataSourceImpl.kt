package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteRefreshTokenDataSource
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

class RemoteRefreshTokenDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
) : RemoteRefreshTokenDataSource {

    override fun reissueRefreshToken(refresh: String): Result<Token> {
        return authApi.requestRefresh(refresh).toResult().map { it.toModel() }
    }
}
