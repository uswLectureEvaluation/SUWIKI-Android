package com.suwiki.lectureevaluation.viewer.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.lectureevaluation.viewer.response.DataResponse
import com.suwiki.lectureevaluation.viewer.response.lecture.LectureDetailEvaluationDataResponse
import com.suwiki.lectureevaluation.viewer.response.lecture.LectureDetailInfoResponse
import com.suwiki.lectureevaluation.viewer.response.lecture.LectureMainResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LectureViewerApi {
    companion object {
        const val LECTURE: String = "/lecture"

        const val QUERY_SEARCH_VALUE = "searchValue"
        const val QUERY_MAJOR_TYPE = "majorType"
        const val QUERY_PAGE = "page"
        const val QUERY_OPTION = "option"
        const val QUERY_LECTURE_ID = "lectureId"

        const val EVALUATE_POST = "/evaluate-posts"
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

    // 검색 결과 자세히 보기 (Evaluation)
    @GET(EVALUATE_POST)
    suspend fun getLectureDetailEvaluation(
        @Query(QUERY_LECTURE_ID) lectureId: Long,
        @Query(QUERY_PAGE) page: Int,
    ): ApiResult<LectureDetailEvaluationDataResponse>
}
