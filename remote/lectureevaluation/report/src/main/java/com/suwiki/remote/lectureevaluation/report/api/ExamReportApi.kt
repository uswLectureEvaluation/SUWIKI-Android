package com.suwiki.remote.lectureevaluation.report.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.report.api.LectureReportApi.Companion.REPORT
import com.suwiki.remote.lectureevaluation.report.api.LectureReportApi.Companion.USER
import com.suwiki.remote.lectureevaluation.report.request.ReportExamRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ExamReportApi {
  companion object {
    const val EXAM_POSTS: String = "/exam-posts"
    const val QUERY_EXAM_IDX = "examIdx"
  }

  // 시험 정보 신고하기
  @POST("$USER/$REPORT/exam")
  suspend fun reportExam(@Body reportExamRequest: ReportExamRequest): ApiResult<Unit>
}
