package com.suwiki.remote.lectureevaluation.editor.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.editor.request.PostLectureExamRequest
import com.suwiki.remote.lectureevaluation.editor.request.UpdateLectureExamRequest
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
}
