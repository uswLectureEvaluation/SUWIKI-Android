package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.api.UserApi.Companion.USER
import com.suwiki.remote.request.user.CheckEmailRequest
import com.suwiki.remote.request.user.CheckIdRequest
import com.suwiki.remote.request.user.SignupRequest
import com.suwiki.remote.response.common.SuccessCheckResponse
import com.suwiki.remote.response.user.OverlapCheckResponse
import retrofit2.http.Body
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface SignUpApi {

    // 회원가입 요청 API
    @POST("$USER/join")
    suspend fun signUp(@Body signupRequest: SignupRequest): ApiResult<SuccessCheckResponse>

    // 아이디 중복 확인 요청 API
    @POST("$USER/check-id")
    suspend fun checkId(@Body checkIdRequest: CheckIdRequest): ApiResult<OverlapCheckResponse>

    // 이메일 중복 확인 요청 API
    @POST("$USER/check-email")
    suspend fun checkEmail(@Body checkEmailRequest: CheckEmailRequest): ApiResult<OverlapCheckResponse>
}
