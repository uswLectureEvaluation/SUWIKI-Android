package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.api.UserApi.Companion.USER
import com.suwiki.remote.response.user.BlacklistResponse
import com.suwiki.remote.response.user.SuspensionHistoryResponse
import retrofit2.http.GET

// TODO : v2 api로 업그레이드 필요.
interface RestrictionHistoryApi {

    // 이용제한 내역 조회
    @GET("$USER/restricted-reason")
    suspend fun getSuspensionHistory(): ApiResult<List<SuspensionHistoryResponse>>

    // 블랙리스트 내역 조회
    @GET("$USER/blacklist-reason")
    suspend fun getBlacklistHistory(): ApiResult<List<BlacklistResponse>>
}
