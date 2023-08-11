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
interface AuthApi {
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

    // 로그인 요청 API
    @POST("$USER/login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResult<TokenResponse>

    // Refresh Token
    @POST("$USER/refresh")
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): ApiResult<TokenResponse>
}
