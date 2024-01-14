package com.suwiki.remote.timetable.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.timetable.response.OpenLectureResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLectureApi {

  @GET("/lecture/current/cells/search")
  suspend fun getOpenLectureList(
    @Query("cursorId") cursorId: Long,
    @Query("size") size: Long,
    @Query("keyword") keyword: String?,
    @Query("major") major: String?,
    @Query("grade") grade: Int?,
  ): ApiResult<OpenLectureResponse>
}
