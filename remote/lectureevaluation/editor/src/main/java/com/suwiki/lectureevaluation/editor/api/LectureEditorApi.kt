package com.suwiki.lectureevaluation.editor.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.lectureevaluation.editor.request.LectureEvaluationRequest
import com.suwiki.lectureevaluation.editor.request.UpdateLectureEvaluationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LectureEditorApi {
    companion object {

        const val EVALUATE_POST = "/evaluate-posts"
        const val QUERY_EVALUATE_ID = "evaluateIdx"
    }

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
}
