package com.suwiki.remote.api

import com.suwiki.data.network.ApiResult
import com.suwiki.remote.api.UserApi.Companion.USER
import com.suwiki.remote.request.BookmarkMajorRequest
import com.suwiki.remote.response.OpenMajorListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface MajorApi {
    companion object {
        const val MAJOR = "$USER/favorite-major/"
        const val MAJOR_TYPE = "majorType"
    }

    // 전공 즐겨찾기 하기
    @POST(MAJOR)
    suspend fun bookmarkMajor(@Body bookmarkMajorRequest: BookmarkMajorRequest): ApiResult<String>

    // 전공 즐겨찾기 조회
    @GET(MAJOR)
    suspend fun getBookmarkMajorList(): ApiResult<OpenMajorListResponse>

    // 전공 즐겨찾기 삭제
    @DELETE(MAJOR)
    suspend fun removeBookmarkMajor(
        @Query(MAJOR_TYPE) majorName: String,
    ): ApiResult<String>
}
