package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.request.user.CheckEmailRequest
import com.suwiki.remote.request.user.CheckIdRequest
import com.suwiki.remote.request.user.FindIdRequest
import com.suwiki.remote.request.user.FindPasswordRequest
import com.suwiki.remote.request.user.LoginRequest
import com.suwiki.remote.request.user.QuitRequest
import com.suwiki.remote.request.user.ResetPasswordRequest
import com.suwiki.remote.request.user.SignupRequest
import com.suwiki.remote.response.user.BlacklistResponse
import com.suwiki.remote.response.user.OverlapCheckResponse
import com.suwiki.remote.response.common.SuccessCheckResponse
import com.suwiki.remote.response.user.SuspensionHistoryResponse
import com.suwiki.remote.response.user.TokenResponse
import com.suwiki.remote.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface UserApi {
    companion object {
        const val USER = "/user"
    }

    // 비밀번호 재설정 API
    @POST("$USER/reset-pw")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): ApiResult<SuccessCheckResponse>

    // 회원탈퇴 요청 API
    @POST("$USER/quit")
    suspend fun quit(@Body quitRequest: QuitRequest): ApiResult<SuccessCheckResponse>

    // 내 정보 페이지 호출 API
    @GET("$USER/my-page")
    suspend fun getUserData(): ApiResult<UserResponse>

    // 이용제한 내역 조회
    @GET("$USER/restricted-reason")
    suspend fun getSuspensionHistory(): ApiResult<List<SuspensionHistoryResponse>>

    // 블랙리스트 내역 조회
    @GET("$USER/blacklist-reason")
    suspend fun getBlacklistHistory(): ApiResult<List<BlacklistResponse>>
}
