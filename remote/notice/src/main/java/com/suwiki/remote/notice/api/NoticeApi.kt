package com.suwiki.remote.notice.api

import com.suwiki.remote.common.retrofit.ApiResult
import com.suwiki.remote.notice.response.DataResponse
import com.suwiki.remote.notice.response.NoticeDetailResponse
import com.suwiki.remote.notice.response.NoticeResponse
import com.suwiki.remote.notice.response.UpdateMandatoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeApi {
  companion object {
    const val NOTICE: String = "/notice"
    const val QUERY_NOTICE_ID = "noticeId"
    const val QUERY_PAGE = "page"
  }

  // 공지사항 리스트 API
  @GET("$NOTICE/all")
  suspend fun getNoticeList(
    @Query(QUERY_PAGE) page: Int,
  ): ApiResult<DataResponse<List<NoticeResponse>>>

  // 공지사항 API
  @GET("$NOTICE/")
  suspend fun getNotice(
    @Query(QUERY_NOTICE_ID) id: Long,
  ): ApiResult<DataResponse<NoticeDetailResponse>>

  @GET("/client/version/update-mandatory")
  suspend fun checkUpdateMandatory(
    @Query("os") os: String = "android",
    @Query("versionCode") versionCode: Long,
  ): ApiResult<UpdateMandatoryResponse>
}
