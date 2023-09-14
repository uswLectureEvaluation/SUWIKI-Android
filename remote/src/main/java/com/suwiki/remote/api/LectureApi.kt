package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.request.evaluation.LectureEvaluationRequest
import com.suwiki.remote.request.evaluation.ReportLectureRequest
import com.suwiki.remote.request.evaluation.UpdateLectureEvaluationRequest
import com.suwiki.remote.response.common.DataResponse
import com.suwiki.remote.response.evaluation.LectureDetailEvaluationDataResponse
import com.suwiki.remote.response.evaluation.MyEvaluationResponse
import com.suwiki.remote.response.lecture.LectureDetailInfoResponse
import com.suwiki.remote.response.lecture.LectureMainResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

        const val EVALUATE_POST = "/evaluate-posts"
        const val REPORT = "report"
        const val QUERY_EVALUATE_ID = "evaluateIdx"
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

    // 내가 쓴 글 (강의평가)
    @GET("$EVALUATE_POST/written")
    suspend fun getEvaluateMyPosts(@Query(QUERY_PAGE) page: Int): ApiResult<DataResponse<List<MyEvaluationResponse>>>

    // 검색 결과 자세히 보기 (Evaluation)
    @GET(EVALUATE_POST)
    suspend fun getLectureDetailEvaluation(
        @Query(QUERY_LECTURE_ID) lectureId: Long,
        @Query(QUERY_PAGE) page: Int,
    ): ApiResult<LectureDetailEvaluationDataResponse>

    // 강의 평가 쓰기
    @POST(EVALUATE_POST)
    suspend fun postLectureEvaluation(
        @Body lectureEvaluationRequest: LectureEvaluationRequest,
    ): ApiResult<Unit>

    // 강의 평가 수정
    @PUT(EVALUATE_POST)
    suspend fun updateLectureEvaluation(
        @Query(QUERY_EVALUATE_ID) lectureId: Long,
        @Body updateLectureEvaluationRequest: UpdateLectureEvaluationRequest,
    ): ApiResult<Unit>

    // 강의 평가 삭제
    @DELETE(EVALUATE_POST)
    suspend fun deleteEvaluation(@Query(QUERY_EVALUATE_ID) id: Long): ApiResult<Unit>

    // 강의 평가 신고하기
    @POST("${UserApi.USER}/$REPORT/evaluate")
    suspend fun reportLecture(@Body reportLectureRequest: ReportLectureRequest): ApiResult<Unit>
}
