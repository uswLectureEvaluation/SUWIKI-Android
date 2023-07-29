package com.suwiki.remote.api

import com.suwiki.data.db.request.BookmarkMajorRequest
import com.suwiki.data.network.ApiResult
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.OpenMajorListDto
import com.suwiki.remote.api.UserApi.Companion.USER
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MajorApi {
    companion object {
        const val MAJOR = "$USER/favorite-major/"
    }

    // 전공 즐겨찾기 하기
    @POST(MAJOR)
    suspend fun bookmarkMajor(@Body bookmarkMajorRequest: BookmarkMajorRequest): ApiResult<String>

    // 전공 즐겨찾기 조회
    @GET(MAJOR)
    suspend fun getBookmarkMajorList(): ApiResult<OpenMajorListDto>

    // 전공 즐겨찾기 삭제
    @DELETE(MAJOR)
    suspend fun clearBookmarkMajor(
        @Query("majorType") majorName: String,
    ): ApiResult<String>
}
