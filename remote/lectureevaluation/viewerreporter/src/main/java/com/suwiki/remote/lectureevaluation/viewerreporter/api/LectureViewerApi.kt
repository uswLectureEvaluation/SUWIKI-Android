package com.suwiki.remote.lectureevaluation.viewerreporter.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.viewerreporter.response.DataResponse
import com.suwiki.remote.lectureevaluation.viewerreporter.response.lecture.LectureEvaluationAverageResponse
import com.suwiki.remote.lectureevaluation.viewerreporter.response.lecture.LectureEvaluationExtraAverageResponse
import com.suwiki.remote.lectureevaluation.viewerreporter.response.lecture.LectureEvaluationListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
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
  @Headers("Domain-Name: suwiki")
  @GET("$LECTURE/all/")
  suspend fun getLectureEvaluationAverageList(
    @Query(QUERY_OPTION) option: String,
    @Query(QUERY_PAGE) page: Int = 1,
    @Query(QUERY_MAJOR_TYPE) majorType: String = "",
  ): ApiResult<DataResponse<List<LectureEvaluationAverageResponse?>>>

  // 통합 검색 결과
  @Headers("Domain-Name: suwiki")
  @GET("$LECTURE/search/")
  suspend fun retrieveLectureEvaluationAverageList(
    @Query(QUERY_SEARCH_VALUE) searchValue: String,
    @Query(QUERY_OPTION) option: String,
    @Query(QUERY_PAGE) page: Int,
    @Query(QUERY_MAJOR_TYPE) majorType: String,
  ): ApiResult<DataResponse<List<LectureEvaluationAverageResponse?>>>

  // 검색결과 자세히 보기 (LECTURE)
  @Headers("Domain-Name: suwiki")
  @GET("$LECTURE/")
  suspend fun getLectureEvaluationExtraAverage(
    @Query(QUERY_LECTURE_ID) lectureId: Long,
  ): ApiResult<DataResponse<LectureEvaluationExtraAverageResponse>>

  // 검색 결과 자세히 보기 (Evaluation)
  @Headers("Domain-Name: suwiki")
  @GET(EVALUATE_POST)
  suspend fun getLectureEvaluationList(
    @Query(QUERY_LECTURE_ID) lectureId: Long,
    @Query(QUERY_PAGE) page: Int,
  ): ApiResult<LectureEvaluationListResponse>
}
