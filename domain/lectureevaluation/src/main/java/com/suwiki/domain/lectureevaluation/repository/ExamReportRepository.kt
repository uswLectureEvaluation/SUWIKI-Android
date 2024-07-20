package com.suwiki.domain.lectureevaluation.repository

interface ExamReportRepository {

  suspend fun reportExam(
    examIdx: Long,
    content: String = "",
  )
}
