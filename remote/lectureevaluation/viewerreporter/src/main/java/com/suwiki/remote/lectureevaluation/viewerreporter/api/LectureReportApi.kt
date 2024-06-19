package com.suwiki.remote.lectureevaluation.viewerreporter.api

import com.suwiki.remote.common.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.viewerreporter.request.ReportLectureRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LectureReportApi {
  companion object {
    const val USER = "/user"
    const val REPORT = "report"
  }

  // 강의 평가 신고하기
  @POST("$USER/$REPORT/evaluate")
  suspend fun reportLecture(@Body reportLectureRequest: ReportLectureRequest): ApiResult<Unit>
}
