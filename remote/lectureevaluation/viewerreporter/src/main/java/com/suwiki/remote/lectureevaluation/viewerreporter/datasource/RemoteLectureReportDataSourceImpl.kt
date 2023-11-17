package com.suwiki.remote.lectureevaluation.viewerreporter.datasource

import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteLectureReportDataSource
import com.suwiki.remote.lectureevaluation.viewerreporter.api.LectureReportApi
import com.suwiki.remote.lectureevaluation.viewerreporter.request.ReportLectureRequest
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
