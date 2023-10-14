package com.suwiki.signup.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.signup.request.CheckEmailRequest
import com.suwiki.signup.request.CheckIdRequest
import com.suwiki.signup.request.SignupRequest
import com.suwiki.signup.response.OverlapCheckResponse
import com.suwiki.signup.response.SuccessCheckResponse
import retrofit2.http.Body
import retrofit2.http.POST

// TODO : v2 api로 업그레이드 필요.
interface SignUpApi {
    companion object {
        const val USER = "/user"
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
}
