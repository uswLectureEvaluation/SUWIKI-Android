package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.api.UserApi.Companion.USER
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
        const val AUTH_HEADER = "Authorization"
    }

    // Refresh Token
    @POST("$USER/refresh")
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): ApiResult<TokenResponse>
}
