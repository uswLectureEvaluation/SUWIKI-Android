package com.suwiki.data.lectureevaluation.repository

import com.suwiki.data.lectureevaluation.datasource.RemoteExamReportDataSource
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
