package com.suwiki.core.network.api

import com.suwiki.core.network.model.TokenResponse
import com.suwiki.core.network.retrofit.ApiResult
import retrofit2.http.Header
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface AuthApi {
  companion object {
    const val USER = "/user"
    const val AUTH_HEADER = "Authorization"
  }

  // Refresh Token
  @POST("$USER/refresh")
  fun reissueRefreshToken(@Header(AUTH_HEADER) refresh: String): ApiResult<TokenResponse>
}
