package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteLectureReportDataSource
import com.suwiki.remote.lectureevaluation.api.LectureReportApi
import com.suwiki.remote.lectureevaluation.request.ReportLectureRequest
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
