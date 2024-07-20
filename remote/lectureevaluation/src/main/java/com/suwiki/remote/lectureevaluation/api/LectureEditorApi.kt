package com.suwiki.remote.lectureevaluation.api

import com.suwiki.remote.common.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.request.PostLectureEvaluationRequest
import com.suwiki.remote.lectureevaluation.request.UpdateLectureEvaluationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LectureEditorApi {
  companion object {

    const val EVALUATE_POST = "/evaluate-posts"
    const val QUERY_EVALUATE_ID = "evaluateIdx"
    const val QUERY_LECTURE_ID = "lectureId"
  }

  // 강의 평가 쓰기
  @POST(EVALUATE_POST)
  suspend fun postLectureEvaluation(
    @Query(QUERY_LECTURE_ID) lectureId: Long,
    @Body postLectureEvaluationRequest: PostLectureEvaluationRequest,
  ): ApiResult<Unit>

  // 강의 평가 수정
  @PUT(EVALUATE_POST)
  suspend fun updateLectureEvaluation(
    @Query(QUERY_EVALUATE_ID) lectureId: Long,
    @Body updateLectureEvaluationRequest: UpdateLectureEvaluationRequest,
  ): ApiResult<Unit>

  // 강의 평가 삭제
  @DELETE(EVALUATE_POST)
  suspend fun deleteLectureEvaluation(@Query(QUERY_EVALUATE_ID) id: Long): ApiResult<Unit>
}
