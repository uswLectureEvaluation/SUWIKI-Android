package com.suwiki.remote.common.api

import com.suwiki.remote.common.di.AUTH_HEADER
import com.suwiki.remote.common.model.TokenResponse
import com.suwiki.remote.common.retrofit.ApiResult
import retrofit2.http.Header
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface AuthApi {
  companion object {
    const val USER = "/user"
  }

  // Refresh Token
  @POST("$USER/refresh")
  suspend fun reissueRefreshToken(@Header(AUTH_HEADER) refresh: String): ApiResult<TokenResponse>
}
