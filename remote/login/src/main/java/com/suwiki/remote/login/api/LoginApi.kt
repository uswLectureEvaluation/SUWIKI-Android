package com.suwiki.remote.login.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.login.request.FindIdRequest
import com.suwiki.remote.login.request.FindPasswordRequest
import com.suwiki.remote.login.request.LoginRequest
import com.suwiki.remote.login.response.SuccessCheckResponse
import com.suwiki.remote.login.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface LoginApi {
  companion object {
    const val USER = "/user"
  }

  // 아이디 찾기 API
  @POST("$USER/find-id")
  suspend fun findId(
    @Body findIdRequest: FindIdRequest,
  ): ApiResult<SuccessCheckResponse>

  // 비밀번호 찾기(임시 비밀번호 전송) API
  @POST("$USER/find-pw")
  suspend fun findPassword(
    @Body findPasswordRequest: FindPasswordRequest,
  ): ApiResult<SuccessCheckResponse>

  // 로그인 요청 API
  @POST("$USER/login")
  suspend fun login(
    @Body loginRequest: LoginRequest,
  ): ApiResult<TokenResponse>
}
