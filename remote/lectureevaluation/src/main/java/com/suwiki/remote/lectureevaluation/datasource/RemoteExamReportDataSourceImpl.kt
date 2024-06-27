package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteExamReportDataSource
import com.suwiki.remote.lectureevaluation.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.request.ReportExamRequest
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
