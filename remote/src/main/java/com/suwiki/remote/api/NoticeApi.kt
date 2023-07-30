package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.api.LectureApi.Companion.QUERY_PAGE
import com.suwiki.remote.response.common.DataResponse
import com.suwiki.remote.response.notice.NoticeDetailResponse
import com.suwiki.remote.response.notice.NoticeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeApi {
    companion object {
        const val NOTICE: String = "/notice"
        const val QUERY_NOTICE_ID = "noticeId"
    }

    // 공지사항 리스트 API
    @GET("$NOTICE/all")
    suspend fun getNoticeList(@Query(QUERY_PAGE) page: Int): ApiResult<DataResponse<List<NoticeResponse>>>

    // 공지사항 API
    @GET(NOTICE)
    suspend fun getNotice(@Query(QUERY_NOTICE_ID) id: Long): ApiResult<DataResponse<NoticeDetailResponse>>
}
