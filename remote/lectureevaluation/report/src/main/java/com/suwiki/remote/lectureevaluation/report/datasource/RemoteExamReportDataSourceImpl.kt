package com.suwiki.remote.lectureevaluation.report.datasource

import com.suwiki.data.lectureevaluation.report.datasource.RemoteExamReportDataSource
import com.suwiki.remote.lectureevaluation.report.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.report.request.ReportExamRequest
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
