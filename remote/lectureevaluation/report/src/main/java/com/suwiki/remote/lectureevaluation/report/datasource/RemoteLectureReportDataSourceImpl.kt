package com.suwiki.remote.lectureevaluation.report.datasource

import com.suwiki.data.lectureevaluation.report.datasource.RemoteLectureReportDataSource
import com.suwiki.remote.lectureevaluation.report.api.LectureReportApi
import com.suwiki.remote.lectureevaluation.report.request.ReportLectureRequest
import javax.inject.Inject

class RemoteLectureReportDataSourceImpl @Inject constructor(
  private val lectureApi: LectureReportApi,
) : RemoteLectureReportDataSource {

  override suspend fun reportLecture(evaluateIdx: Long, content: String) {
    return lectureApi.reportLecture(
      ReportLectureRequest(
        evaluateIdx = evaluateIdx,
        content = content,
      ),
    ).getOrThrow()
  }
}
