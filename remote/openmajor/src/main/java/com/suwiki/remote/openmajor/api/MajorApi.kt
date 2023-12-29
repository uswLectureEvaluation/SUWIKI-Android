package com.suwiki.remote.openmajor.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.openmajor.request.BookmarkMajorRequest
import com.suwiki.remote.openmajor.response.OpenMajorListResponse
import com.suwiki.remote.openmajor.response.OpenMajorVersionResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface MajorApi {
  companion object {
    const val MAJOR = "/user/favorite-major/"
    const val MAJOR_TYPE = "majorType"
    const val SUWIKI = "/suwiki"
    const val OPEN_MAJOR_VERSION = "$SUWIKI/version/"
    const val OPEN_MAJOR_LIST_UPDATE = "$SUWIKI/$MAJOR_TYPE"
  }

  // 전공 버전 조회
  @GET(OPEN_MAJOR_VERSION)
  suspend fun getOpenMajorVersion(): ApiResult<OpenMajorVersionResponse>

  // 전공 리스트 조회
  @GET(OPEN_MAJOR_LIST_UPDATE)
  suspend fun getOpenMajorList(): ApiResult<OpenMajorListResponse>

  // 전공 즐겨찾기 하기
  @POST(MAJOR)
  suspend fun bookmarkMajor(@Body bookmarkMajorRequest: BookmarkMajorRequest): ApiResult<Unit>

  // 전공 즐겨찾기 조회
  @GET(MAJOR)
  suspend fun getBookmarkMajorList(): ApiResult<OpenMajorListResponse>

  // 전공 즐겨찾기 삭제
  @DELETE(MAJOR)
  suspend fun removeBookmarkMajor(
    @Query(MAJOR_TYPE) majorName: String,
  ): ApiResult<Unit>
}
