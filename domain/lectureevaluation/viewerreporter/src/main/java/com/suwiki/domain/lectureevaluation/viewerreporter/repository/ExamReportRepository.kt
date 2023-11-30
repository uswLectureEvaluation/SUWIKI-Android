package com.suwiki.domain.lectureevaluation.viewerreporter.repository

interface ExamReportRepository {

  suspend fun reportExam(
    evaluateIdx: Long,
    content: String = "",
  )
}
