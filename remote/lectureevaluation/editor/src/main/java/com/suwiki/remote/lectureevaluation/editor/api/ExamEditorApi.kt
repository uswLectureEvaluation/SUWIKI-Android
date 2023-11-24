package com.suwiki.remote.lectureevaluation.editor.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.editor.request.PostExamEvaluationRequest
import com.suwiki.remote.lectureevaluation.editor.request.UpdateExamEvaluationRequest
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
