package com.suwiki.data.lectureevaluation.viewerreporter.repository

import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteExamReportDataSource
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamReportRepository
import javax.inject.Inject

class ExamReportRepositoryImpl @Inject constructor(
  private val remoteExamReportDataSource: RemoteExamReportDataSource,
) : ExamReportRepository {

  override suspend fun reportExam(examIdx: Long, content: String) {
    remoteExamReportDataSource.reportExam(
      examIdx = examIdx,
      content = content,
    )
  }
}
