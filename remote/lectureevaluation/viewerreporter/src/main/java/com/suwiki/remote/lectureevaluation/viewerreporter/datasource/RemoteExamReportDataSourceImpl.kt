package com.suwiki.remote.lectureevaluation.viewerreporter.datasource

import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteExamReportDataSource
import com.suwiki.remote.lectureevaluation.viewerreporter.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.viewerreporter.request.ReportExamRequest
import javax.inject.Inject

class RemoteExamReportDataSourceImpl @Inject constructor(
  private val examApi: ExamReportApi,
) : RemoteExamReportDataSource {

  override suspend fun reportExam(
    examIdx: Long,
    content: String,
  ) {
    return examApi.reportExam(
      ReportExamRequest(
        examIdx = examIdx,
        content = content,
      ),
    ).getOrThrow()
  }
}
