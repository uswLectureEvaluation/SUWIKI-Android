package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.api.LectureApi.Companion.QUERY_LECTURE_ID
import com.suwiki.remote.api.LectureApi.Companion.QUERY_PAGE
import com.suwiki.remote.api.UserApi.Companion.USER
import com.suwiki.remote.request.LectureEvaluationRequest
import com.suwiki.remote.request.ReportLectureRequest
import com.suwiki.remote.request.UpdateLectureEvaluationRequest
import com.suwiki.remote.response.DataResponse
import com.suwiki.remote.response.LectureDetailEvaluationDataResponse
import com.suwiki.remote.response.MyEvaluationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface EvaluateApi {
    companion object {
        const val EVALUATE_POST = "/evaluate-posts"
        const val REPORT = "report"
        const val QUERY_EVALUATE_ID = "evaluateIdx"
    }

    // 내가 쓴 글 (강의평가)
    @GET("$EVALUATE_POST/written")
    suspend fun getEvaluatePosts(@Query(QUERY_PAGE) page: Int): ApiResult<DataResponse<List<MyEvaluationResponse>>>

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
    @POST("$USER/$REPORT/evaluate")
    suspend fun reportLecture(@Body reportLectureRequest: ReportLectureRequest): ApiResult<Unit>
}
