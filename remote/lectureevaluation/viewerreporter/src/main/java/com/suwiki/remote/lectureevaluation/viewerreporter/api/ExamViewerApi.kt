package com.suwiki.remote.lectureevaluation.viewerreporter.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.viewerreporter.api.LectureViewerApi.Companion.QUERY_LECTURE_ID
import com.suwiki.remote.lectureevaluation.viewerreporter.response.exam.LectureDetailExamDataResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExamViewerApi {
  companion object {
    const val EXAM_POSTS: String = "/exam-posts"
    const val QUERY_PAGE = "page"
  }

  // 검색결과 자세히 보기 (Exam)
  @GET(EXAM_POSTS)
  suspend fun getLectureDetailExam(
    @Query(QUERY_LECTURE_ID) lectureId: Long,
    @Query(QUERY_PAGE) page: Int,
  ): ApiResult<LectureDetailExamDataResponse>

  // 시험 정보 구매
  @POST("$EXAM_POSTS/purchase/")
  suspend fun buyExam(@Query(QUERY_LECTURE_ID) lectureId: Long): ApiResult<Unit>
}
