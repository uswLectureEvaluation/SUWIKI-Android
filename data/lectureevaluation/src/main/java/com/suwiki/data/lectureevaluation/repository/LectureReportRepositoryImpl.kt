package com.suwiki.data.lectureevaluation.repository

import com.suwiki.data.lectureevaluation.datasource.RemoteLectureReportDataSource
import com.suwiki.domain.lectureevaluation.repository.LectureReportRepository
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
