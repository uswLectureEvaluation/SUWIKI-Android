package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.response.common.DataResponse
import com.suwiki.remote.response.lecture.LectureDetailInfoResponse
import com.suwiki.remote.response.lecture.LectureMainResponse
import retrofit2.http.GET
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface LectureApi {
    companion object {
        const val LECTURE: String = "/lecture"

        const val QUERY_SEARCH_VALUE = "searchValue"
        const val QUERY_MAJOR_TYPE = "majorType"
        const val QUERY_PAGE = "page"
        const val QUERY_OPTION = "option"
        const val QUERY_LECTURE_ID = "lectureId"
    }

    // 메인 페이지
    @GET("$LECTURE/all/")
    suspend fun getLectureMainList(
        @Query(QUERY_OPTION) option: String,
        @Query(QUERY_PAGE) page: Int = 1,
        @Query(QUERY_MAJOR_TYPE) majorType: String = "",
    ): ApiResult<DataResponse<List<LectureMainResponse?>>>

    // 통합 검색 결과
    @GET("$LECTURE/search/")
    suspend fun getSearchResultDetail(
        @Query(QUERY_SEARCH_VALUE) searchValue: String,
        @Query(QUERY_OPTION) option: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_MAJOR_TYPE) majorType: String,
    ): ApiResult<DataResponse<List<LectureMainResponse?>>>

    // 검색결과 자세히 보기 (LECTURE)
    @GET("$LECTURE/")
    suspend fun getLectureDetailInfo(@Query(QUERY_LECTURE_ID) lectureId: Long): ApiResult<DataResponse<LectureDetailInfoResponse>>
}
