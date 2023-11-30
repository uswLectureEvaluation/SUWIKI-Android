package com.suwiki.data.lectureevaluation.viewerreporter.repository

import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteLectureReportDataSource
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureReportRepository
import javax.inject.Inject

class LectureReportRepositoryImpl @Inject constructor(
  private val remoteLectureReportDataSource: RemoteLectureReportDataSource,
) : LectureReportRepository {

  override suspend fun reportLecture(evaluateIdx: Long, content: String) {
    remoteLectureReportDataSource.reportLecture(
      evaluateIdx = evaluateIdx,
      content = content,
    )
  }
}
