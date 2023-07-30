package com.suwiki.remote.api

import com.suwiki.remote.ApiResult
import com.suwiki.remote.api.EvaluateApi.Companion.REPORT
import com.suwiki.remote.api.LectureApi.Companion.QUERY_LECTURE_ID
import com.suwiki.remote.api.LectureApi.Companion.QUERY_PAGE
import com.suwiki.remote.api.UserApi.Companion.USER
import com.suwiki.remote.request.PostLectureExamRequest
import com.suwiki.remote.request.ReportExamRequest
import com.suwiki.remote.request.UpdateLectureExamRequest
import com.suwiki.remote.response.DataResponse
import com.suwiki.remote.response.LectureDetailExamDataResponse
import com.suwiki.remote.response.LectureExamResponse
import com.suwiki.remote.response.PurchaseHistoryResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface ExamApi {
    companion object {
        const val EXAM_POSTS: String = "/exam-posts"
        const val QUERY_EXAM_IDX = "examIdx"
    }

    // 내가 쓴 글 (시험 정보)
    @GET("$EXAM_POSTS/written")
    suspend fun getExamPosts(@Query(QUERY_PAGE) page: Int): ApiResult<DataResponse<List<LectureExamResponse>>>

    // 시험정보 구매 이력
    @GET("$EXAM_POSTS/purchase")
    suspend fun getPurchaseHistory(): ApiResult<DataResponse<List<PurchaseHistoryResponse>>>

    // 검색결과 자세히 보기 (Exam)
    @GET(EXAM_POSTS)
    suspend fun getLectureDetailExam(
        @Query(QUERY_LECTURE_ID) lectureId: Long,
        @Query(QUERY_PAGE) page: Int,
    ): ApiResult<LectureDetailExamDataResponse>

    // 시험 정보 구매
    @POST("$EXAM_POSTS/purchase/")
    suspend fun buyExam(@Query(QUERY_LECTURE_ID) lectureId: Long): ApiResult<Unit>

    // 시험정보 쓰기
    @POST(EXAM_POSTS)
    suspend fun postLectureExam(
        @Query(QUERY_LECTURE_ID) lectureId: Long,
        @Body request: PostLectureExamRequest,
    ): ApiResult<Unit>

    // 시험 정보 수정
    @PUT(EXAM_POSTS)
    suspend fun updateLectureExam(
        @Query(QUERY_EXAM_IDX) lectureId: Long,
        @Body request: UpdateLectureExamRequest,
    ): ApiResult<Unit>

    // 시험 정보 삭제
    @DELETE(EXAM_POSTS)
    suspend fun deleteExamInfo(@Query(QUERY_EXAM_IDX) id: Long): ApiResult<Unit>

    // 시험 정보 신고하기
    @POST("$USER/$REPORT/exam")
    suspend fun reportExam(@Body reportExamRequest: ReportExamRequest): ApiResult<Unit>
}
