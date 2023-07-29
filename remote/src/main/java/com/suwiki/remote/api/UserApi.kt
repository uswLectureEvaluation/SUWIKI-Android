package com.suwiki.remote.api

import com.suwiki.data.db.request.CheckEmailRequest
import com.suwiki.data.db.request.CheckIdRequest
import com.suwiki.data.db.request.FindIdRequest
import com.suwiki.data.db.request.FindPasswordRequest
import com.suwiki.data.db.request.LoginRequest
import com.suwiki.data.db.request.QuitRequest
import com.suwiki.data.db.request.ResetPasswordRequest
import com.suwiki.data.db.request.SignupRequest
import com.suwiki.data.model.Token
import com.suwiki.data.network.ApiResult
import com.suwiki.data.network.TokenAuthenticator
import com.suwiki.data.network.dto.BlacklistDto
import com.suwiki.data.network.dto.OverlapCheckDto
import com.suwiki.data.network.dto.SuccessCheckDto
import com.suwiki.data.network.dto.SuspensionHistoryDto
import com.suwiki.data.network.dto.UserDataDto
import retrofit2.Call
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
    suspend fun signUp(@Body signupRequest: SignupRequest): ApiResult<SuccessCheckDto>

    // 아이디 중복 확인 요청 API
    @POST("$USER/check-id")
    suspend fun checkId(@Body checkIdRequest: CheckIdRequest): ApiResult<OverlapCheckDto>

    // 이메일 중복 확인 요청 API
    @POST("$USER/check-email")
    suspend fun checkEmail(@Body checkEmailRequest: CheckEmailRequest): ApiResult<OverlapCheckDto>

    // 아이디 찾기 API
    @POST("$USER/find-id")
    suspend fun findId(@Body findIdRequest: FindIdRequest): ApiResult<SuccessCheckDto>

    // 비밀번호 찾기(임시 비밀번호 전송) API
    @POST("$USER/find-pw")
    suspend fun findPassword(@Body findPasswordRequest: FindPasswordRequest): ApiResult<SuccessCheckDto>

    // 비밀번호 재설정 API
    @POST("$USER/reset-pw")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): ApiResult<SuccessCheckDto>

    // 로그인 요청 API
    @POST("$USER/login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResult<Token>

    // Refresh Token
    @POST("$USER/refresh")
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): Call<Token>

    // 회원탈퇴 요청 API
    @POST("$USER/quit")
    suspend fun quit(@Body quitRequest: QuitRequest): ApiResult<SuccessCheckDto>

    // 내 정보 페이지 호출 API
    @GET("$USER/my-page")
    suspend fun getUserData(): Response<UserDataDto>

    // 이용제한 내역 조회
    @GET("$USER/restricted-reason")
    suspend fun getSuspensionHistory(): ApiResult<List<SuspensionHistoryDto>>

    // 블랙리스트 내역 조회
    @GET("$USER/blacklist-reason")
    suspend fun getBlacklistHistory(): ApiResult<List<BlacklistDto>>
}
