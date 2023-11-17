package com.suwiki.remote.lectureevaluation.viewerreporter.datasource

import com.suwiki.data.lectureevaluation.report.datasource.RemoteExamReportDataSource
import com.suwiki.remote.lectureevaluation.viewerreporter.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.viewerreporter.request.ReportExamRequest
import javax.inject.Inject

class RemoteExamReportDataSourceImpl @Inject constructor(
  private val examApi: ExamReportApi,
) : RemoteExamReportDataSource {

  override suspend fun reportExam(
    evaluateIdx: Long,
    content: String,
  ) {
    return examApi.reportExam(
      ReportExamRequest(
        evaluateIdx = evaluateIdx,
        content = content,
      ),
    ).getOrThrow()
  }
}
