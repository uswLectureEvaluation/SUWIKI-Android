package com.suwiki.remote.lectureevaluation.api

import com.suwiki.remote.common.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.api.LectureReportApi.Companion.REPORT
import com.suwiki.remote.lectureevaluation.api.LectureReportApi.Companion.USER
import com.suwiki.remote.lectureevaluation.request.ReportExamRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ExamReportApi {

  // 시험 정보 신고하기
  @POST("$USER/$REPORT/exam")
  suspend fun reportExam(@Body reportExamRequest: ReportExamRequest): ApiResult<Unit>
}
