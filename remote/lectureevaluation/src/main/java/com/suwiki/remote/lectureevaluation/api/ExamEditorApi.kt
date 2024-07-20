package com.suwiki.remote.lectureevaluation.api

import com.suwiki.remote.common.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.request.PostExamEvaluationRequest
import com.suwiki.remote.lectureevaluation.request.UpdateExamEvaluationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface ExamEditorApi {
  companion object {
    const val EXAM_POSTS: String = "/exam-posts"
    const val QUERY_EXAM_IDX = "examIdx"
    const val QUERY_LECTURE_ID = "lectureId"
  }

  @POST(EXAM_POSTS)
  suspend fun postExamEvaluation(
    @Query(QUERY_LECTURE_ID) lectureId: Long,
    @Body request: PostExamEvaluationRequest,
  ): ApiResult<Unit>

  @PUT(EXAM_POSTS)
  suspend fun updateExamEvaluation(
    @Query(QUERY_EXAM_IDX) lectureId: Long,
    @Body request: UpdateExamEvaluationRequest,
  ): ApiResult<Unit>

  @DELETE(EXAM_POSTS)
  suspend fun deleteExamEvaluation(@Query(QUERY_EXAM_IDX) id: Long): ApiResult<Unit>
}
