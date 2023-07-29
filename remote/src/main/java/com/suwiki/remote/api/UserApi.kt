package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.request.CheckEmailRequest
import com.suwiki.remote.request.CheckIdRequest
import com.suwiki.remote.request.FindIdRequest
import com.suwiki.remote.request.FindPasswordRequest
import com.suwiki.remote.request.LoginRequest
import com.suwiki.remote.request.QuitRequest
import com.suwiki.remote.request.ResetPasswordRequest
import com.suwiki.remote.request.SignupRequest
import com.suwiki.remote.response.BlacklistResponse
import com.suwiki.remote.response.OverlapCheckResponse
import com.suwiki.remote.response.SuccessCheckResponse
import com.suwiki.remote.response.SuspensionHistoryResponse
import com.suwiki.remote.response.TokenResponse
import com.suwiki.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface UserApi {
    companion object {
        const val USER = "/user"
        const val AUTH_HEADER = "Authorization"
    }

    // 회원가입 요청 API
    @POST("$USER/join")
    suspend fun signUp(@Body signupRequest: SignupRequest): ApiResult<SuccessCheckResponse>

    // 아이디 중복 확인 요청 API
    @POST("$USER/check-id")
    suspend fun checkId(@Body checkIdRequest: CheckIdRequest): ApiResult<OverlapCheckResponse>

    // 이메일 중복 확인 요청 API
    @POST("$USER/check-email")
    suspend fun checkEmail(@Body checkEmailRequest: CheckEmailRequest): ApiResult<OverlapCheckResponse>

    // 아이디 찾기 API
    @POST("$USER/find-id")
    suspend fun findId(@Body findIdRequest: FindIdRequest): ApiResult<SuccessCheckResponse>

    // 비밀번호 찾기(임시 비밀번호 전송) API
    @POST("$USER/find-pw")
    suspend fun findPassword(@Body findPasswordRequest: FindPasswordRequest): ApiResult<SuccessCheckResponse>

    // 비밀번호 재설정 API
    @POST("$USER/reset-pw")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): ApiResult<SuccessCheckResponse>

    // 로그인 요청 API
    @POST("$USER/login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResult<TokenResponse>

    // Refresh Token
    @POST("$USER/refresh")
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): ApiResult<TokenResponse>

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
