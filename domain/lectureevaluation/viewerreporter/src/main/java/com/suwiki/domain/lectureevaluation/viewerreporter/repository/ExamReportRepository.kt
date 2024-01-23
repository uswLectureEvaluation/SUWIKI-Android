package com.suwiki.domain.lectureevaluation.viewerreporter.repository

interface ExamReportRepository {

  suspend fun reportExam(
    examIdx: Long,
    content: String = "",
  )
}
