package com.suwiki.remote.user.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.user.request.QuitRequest
import com.suwiki.remote.user.request.ResetPasswordRequest
import com.suwiki.remote.user.response.BanHistoryResponse
import com.suwiki.remote.user.response.BlackListResponse
import com.suwiki.remote.user.response.SuccessCheckResponse
import com.suwiki.remote.user.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface UserApi {
  companion object {
    const val USER = "/user"
  }

  // 비밀번호 재설정 API
  @POST("$USER/reset-pw")
  suspend fun resetPassword(
    @Body resetPasswordRequest: ResetPasswordRequest,
  ): ApiResult<SuccessCheckResponse>

  // 회원탈퇴 요청 API
  @POST("$USER/quit")
  suspend fun quit(
    @Body quitRequest: QuitRequest,
  ): ApiResult<SuccessCheckResponse>

  // 내 정보 페이지 호출 API
  @Headers("Domain-Name: suwiki")
  @GET("$USER/my-page")
  suspend fun getUserData(): ApiResult<UserResponse>

  // 이용제한 내역 조회 API
  @GET("$USER/restricted-reason")
  suspend fun getBanHistoryData(): ApiResult<List<BanHistoryResponse>>

  // 블랙리스트 내역 조회 API
  @GET("$USER/blacklist-reason")
  suspend fun getBlackListData(): ApiResult<List<BlackListResponse>>
}
